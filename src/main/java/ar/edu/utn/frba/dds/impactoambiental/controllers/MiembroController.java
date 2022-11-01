package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.ServiceLocator;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TramoDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TrayectoResumenDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoEnTransportePublico;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeLineas;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeSectores;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioMediosDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import com.google.common.collect.ImmutableMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MiembroController implements Controlador {
  private RepositorioDeSectores sectores = RepositorioDeSectores.getInstance();
  private RepositorioOrganizaciones organizaciones = RepositorioOrganizaciones.getInstance();
  private Geolocalizador geolocalizador = new Geolocalizador(ServiceLocator.getServiceLocator().getGeoDdsApiKey());

  private UsuarioMiembro usuarioMiembroDeSesion(Request req) {
    return req.session().<UsuarioMiembro>attribute("usuario");
  }

  private Either<Miembro> obtenerMiembro(Request req) {
    return Optional.ofNullable(req.params("miembro"))
        .map(Either::exitoso)
        .orElseGet(() -> Either.fallido("No se especificó un miembro"))
        .apply(Long::parseLong, "El id del miembro debe ser un número")
        .flatMap(usuarioMiembroDeSesion(req)::getMiembro);
  }

  private List<VinculacionDto> obtenerVinculacionesDto(UsuarioMiembro usuario) {
    List<VinculacionDto> vinculaciones = usuario.getMiembros().stream()
      .map(m -> new VinculacionDto(
        null,
        m,
        organizaciones.buscarPorMiembro(m).get(),
        null,
        null
      )).collect(Collectors.toList());

    vinculaciones.forEach(v -> {
      v.setSector(v.getOrganizacion().getSectores().stream()
        .filter(s -> s.getMiembros().contains(v.getMiembro()))
        .findFirst().get());
      v.setEstado(v.getSector().getVinculaciones().stream()
        .filter(vinc -> vinc.getMiembro().equals(v.getMiembro()))
        .findFirst().get().getEstado());
      v.setId(v.getSector().getVinculaciones().stream().
        filter(vinc -> vinc.getMiembro().equals(v.getMiembro()))
        .findFirst().get().getId());
    });

    return vinculaciones;
  }

  private List<TrayectoResumenDto> obtenerTrayectosDto(Miembro miembro) {
    return miembro.getTrayectos().stream()
      .map(t -> new TrayectoResumenDto(
        t.getFecha(),
        t.getCodigoInvite(),
        t.getTramos().get(0).nombreOrigen(),
        t.getTramos().get(t.getTramos().size() - 1).nombreDestino()
      ))
      .collect(Collectors.toList());
  }

  private List<Tramo> obtenerPretramos(Miembro miembro, Request req) {
    Map<Miembro, List<Tramo>> miembrosPretramos = req.session().attribute("pretramos");
    if (miembrosPretramos == null) {
      miembrosPretramos = new HashMap<>();
      req.session().attribute("pretramos", miembrosPretramos);
    }

    return miembrosPretramos.computeIfAbsent(miembro, k -> new ArrayList<>());
  }

  private void limpiarPretramos(Miembro miembro, Request req) {
    Map<Miembro, List<Tramo>> miembrosPretramos = req.session().attribute("pretramos");
    if (miembrosPretramos != null) {
      miembrosPretramos.remove(miembro);
    }
  }

  private Either<Linea> obtenerLinea(Request req) {
    return Form.of(req).getParamOrError("linea", "Es necesario indicar una linea")
      .apply(s -> RepositorioDeLineas.getInstance().obtenerPorID(Long.parseLong(s)).get(), "La linea no existe");
  }

  private Either<MedioDeTransporte> obtenerMedioDeTransporte(Request req) {
    return Form.of(req).getParamOrError("medioDeTransporte", "Es necesario indicar un medio de transporte")
      .apply(s -> RepositorioMediosDeTransporte.getInstance().obtenerPorID(Long.parseLong(s)).get(), "El medio de transporte no existe");
  }

  public ModelAndView vinculaciones(Request request, Response response) {
    UsuarioMiembro usuario = usuarioMiembroDeSesion(request);
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", usuario,
      "vinculaciones", vinculaciones
    );
    return new ModelAndView(model, "vinculacionesMiembro.html.hbs");
  }

  public ModelAndView proponerVinculacion(Request request, Response response) {
    UsuarioMiembro usuario = usuarioMiembroDeSesion(request);

    return Form.of(request).getParamOrError("codigoInvite", "Es requerido un codigo")
      .apply(UUID::fromString, "El codigo ingresado no tiene el formato correcto")
      .flatMap(sectores::buscarPorCodigoInvite)
      .fold(
        errores -> {
          response.redirect("/miembro/" + usuario.getId() + "/vinculaciones?errores=" + encode(String.join(", ", errores)));
          return null;
        },
        sector -> {
          Miembro membresia = new Miembro(usuario);
          usuario.agregarMiembro(membresia);
          sector.solicitarVinculacion(new Vinculacion(membresia));
          response.redirect("/");
          return null;
        }
      );
  }

  public ModelAndView trayectos(Request request, Response response) {
    UsuarioMiembro usuario = usuarioMiembroDeSesion(request);
    Miembro miembro = obtenerMiembro(request).getValor();
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);

    List<TrayectoResumenDto> trayectos = obtenerTrayectosDto(miembro);

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", usuario,
      "miembro", miembro,
      "vinculaciones", vinculaciones,
      "trayectos", trayectos
    );
    return new ModelAndView(model, "trayectosMiembro.html.hbs");
  }

  public ModelAndView nuevoTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = usuarioMiembroDeSesion(request);
    Miembro miembro = obtenerMiembro(request).getValor();
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    List<Tramo> pretramos = obtenerPretramos(miembro, request);
    List<Linea> lineas = RepositorioDeLineas.getInstance().obtenerTodos();
    List<MedioDeTransporte> mediosDeTransporte = RepositorioMediosDeTransporte.getInstance().obtenerTodos();

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", usuario,
      "miembro", miembro,
      "vinculaciones", vinculaciones,
      "pretramos", TramoDto.ofList(pretramos),
      "lineas", lineas,
      "mediosDeTransporte", mediosDeTransporte
    );
    return new ModelAndView(model, "nuevoTrayecto.html.hbs");
  }

  public ModelAndView anadirTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = usuarioMiembroDeSesion(request);
    Miembro miembro = obtenerMiembro(request).getValor();
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    List<Tramo> pretramos = obtenerPretramos(miembro, request);

    //Dejo solo este chequeo porque no es sobre el formulario
    if(pretramos.isEmpty()){
      response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" + miembro.getId() 
        + "/trayectos/nuevo?errores=El trayecto necesita al menos un tramo"); 
      return null;
    }

    //params del form
    LocalDate fechaTrayecto = LocalDate.parse(Form.of(request).getParam("fecha").get());

    miembro.darDeAltaTrayecto(new Trayecto(fechaTrayecto, pretramos));
    limpiarPretramos(miembro, request);

    response.redirect("/miembros/" + usuario.getId() + "/vinculaciones/" + miembro.getId() + "/trayectos"); 
    return null;
  }

  public ModelAndView nuevoTramo(Request request, Response response) {
    UsuarioMiembro usuario = usuarioMiembroDeSesion(request);
    Miembro miembro = obtenerMiembro(request).getValor();
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    List<TramoDto> pretramosDtos = TramoDto.ofList(obtenerPretramos(miembro, request));

    if (request.queryParams("tipo").equals("publico")) {
      //param de form
      Linea linea = obtenerLinea(request).getValor();
      
      ImmutableMap<String, Object> model = ImmutableMap.of(
        "usuario", usuario,
        "miembro", miembro,
        "vinculaciones", vinculaciones,
        "pretramos", pretramosDtos,
        "linea", linea
      );
      return new ModelAndView(model, "nuevoTramoPublico.html.hbs");
    }
    else {
      //param de form
      MedioDeTransporte medio = obtenerMedioDeTransporte(request).getValor();

      ImmutableMap<String, Object> model = ImmutableMap.of(
        "usuario", usuario,
        "miembro", miembro,
        "vinculaciones", vinculaciones,
        "pretramos", pretramosDtos,
        "medio", medio
      );
      return new ModelAndView(model, "nuevoTramoPrivado.html.hbs");
    }
  }

  public ModelAndView anadirTramo(Request request, Response response) {
    UsuarioMiembro usuario = usuarioMiembroDeSesion(request);
    Miembro miembro = obtenerMiembro(request).getValor();
    List<Tramo> pretramos = obtenerPretramos(miembro, request);

    if ( request.queryParams("tipo").equals("publico") ) {
      //params del form
      Linea linea = obtenerLinea(request).getValor();

      Long origenID = Long.parseLong(Form.of(request).getParam("origen").get());
      Parada origen = linea.getParadas().stream().filter(p -> p.getId() == origenID).findFirst().get();
      Long destinoID = Long.parseLong(Form.of(request).getParam("destino").get());
      Parada destino = linea.getParadas().stream().filter(p -> p.getId() == destinoID).findFirst().get();

      pretramos.add(new TramoEnTransportePublico(origen, destino, linea));
    }
    else {
      //params del form
      MedioDeTransporte medio = obtenerMedioDeTransporte(request).getValor();
      
      String paisOrigen = Form.of(request).getParam("paisOrigen").get();
      String provinciaOrigen = Form.of(request).getParam("provinciaOrigen").get();
      String municipioOrigen = Form.of(request).getParam("municipioOrigen").get();
      String localidadOrigen = Form.of(request).getParam("localidadOrigen").get();
      String calleOrigen = Form.of(request).getParam("calleOrigen").get();
      String alturaOrigen = Form.of(request).getParam("alturaOrigen").get();

      Ubicacion origen = geolocalizador.getUbicacion(
        paisOrigen, 
        provinciaOrigen,
        municipioOrigen, 
        localidadOrigen, 
        calleOrigen, 
        alturaOrigen
      ).get();

      String paisDestino = Form.of(request).getParam("paisDestino").get();
      String provinciaDestino = Form.of(request).getParam("provinciaDestino").get();
      String municipioDestino = Form.of(request).getParam("municipioDestino").get();
      String localidadDestino = Form.of(request).getParam("localidadDestino").get();
      String calleDestino = Form.of(request).getParam("calleDestino").get();
      String alturaDestino = Form.of(request).getParam("alturaDestino").get();

      Ubicacion destino = geolocalizador.getUbicacion(
        paisDestino, 
        provinciaDestino,
        municipioDestino, 
        localidadDestino, 
        calleDestino, 
        alturaDestino
      ).get();

      pretramos.add(new TramoPrivado(geolocalizador, origen, destino, medio));
    }

    response.redirect("/miembros/" + usuario.getId() + "/vinculaciones/" + miembro.getId() + "/trayectos/nuevo");
    return null;
  }
}
