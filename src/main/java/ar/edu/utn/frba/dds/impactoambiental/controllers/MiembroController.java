package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.controllers.helpers.MiembrosHelper;
import ar.edu.utn.frba.dds.impactoambiental.controllers.helpers.TramosHelper;
import ar.edu.utn.frba.dds.impactoambiental.controllers.helpers.TrayectosHelper;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TramoDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TrayectoResumenDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionException;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoEnTransportePublico;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TramoPrivado;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.EstadoVinculo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeLineas;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeSectores;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioMediosDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioVinculaciones;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.UUID;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import static ar.edu.utn.frba.dds.impactoambiental.controllers.helpers.MiembrosHelper.determinarVinculacionSeleccionada;

public class MiembroController implements Controller {
  private RepositorioDeSectores sectores = RepositorioDeSectores.getInstance();
  private RepositorioOrganizaciones organizaciones = RepositorioOrganizaciones.getInstance();
  private RepositorioVinculaciones vinculaciones = RepositorioVinculaciones.getInstance();
  private Geolocalizador geolocalizador;
  private TramosHelper tramosHelper = new TramosHelper();
  private MiembrosHelper miembrosHelper = new MiembrosHelper();
  private TrayectosHelper trayectosHelper = new TrayectosHelper();

  public MiembroController(Geolocalizador geolocalizador) {
    this.geolocalizador = geolocalizador;
  }

  public void validarVinculacion(Request request, Response response) {
    Context ctx = Context.of(request);
    ctx.getPathParam("vinculacion", "INVALID_REQUEST")
        .apply(Long::valueOf, "INVALID_REQUEST")
        .flatApply(vinculaciones::obtenerPorID, "NOT_FOUND")
        .filter(vinculacion -> ctx.<UsuarioMiembro>getRequestAttribute("usuario", "INTERNAL_SERVER_ERROR")
            .getValor().tieneVinculacion(vinculacion), "FORBIDDEN")
        .filter(vinculacion -> vinculacion.getEstado().equals(EstadoVinculo.ACEPTADO), "FORBIDDEN")
        .fold(ValidacionException::new, vinculacion -> {
          ctx.setRequestAttribute("vinculacion", vinculacion);
          return null;
        });
  }

  public ModelAndView vinculaciones(Request request, Response response) {
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    List<VinculacionDto> vinculaciones = miembrosHelper.obtenerVinculacionesDto(Context.of(request));

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", usuario,
      "vinculaciones", vinculaciones,
      "vinculacionesSidebarSelected", true,
      "trayectosSidebarSelected", false
    );
    return new ModelAndView(model, "pages/usuarios/vinculaciones/index.html.hbs");
  }

  public ModelAndView proponerVinculacion(Request request, Response response) {
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();

    return Form.of(request).getParamOrError("codigoInvite", "Es requerido un codigo")
      .apply(UUID::fromString, "El codigo ingresado no tiene el formato correcto")
      .flatApply(sectores::buscarPorCodigoInvite, "El código ingresado no existe")
      .fold(
        errores -> {
          response.redirect("/usuarios/me/vinculaciones?errores=" + encode(String.join(", ", errores)));
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
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    Miembro miembro = miembrosHelper.obtenerMiembroDesdeAttributes(Context.of(request)).getValor();
    List<VinculacionDto> vinculaciones = miembrosHelper.obtenerVinculacionesDto(Context.of(request));
    List<TrayectoResumenDto> trayectos = miembrosHelper.obtenerTrayectosDto(Context.of(request));
    Vinculacion vinculacion = request.attribute("vinculacion");
    VinculacionDto vinculacionElegida = determinarVinculacionSeleccionada(vinculaciones, vinculacion);

    ImmutableMap<String, Object> model = ImmutableMap.of(
        "usuario", usuario,
        "miembro", miembro,
        "vinculaciones", vinculaciones,
        "trayectos", trayectos,
        "vinculacion", vinculacion,
        "vinculacionElegida", vinculacionElegida,
        "vinculacionesSidebarSelected", false,
        "trayectosSidebarSelected", true
    );
    return new ModelAndView(model, "pages/usuarios/vinculaciones/trayectos/index.html.hbs");
  }

  public ModelAndView nuevoTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    Miembro miembro = miembrosHelper.obtenerMiembroDesdeAttributes(Context.of(request)).getValor();
    List<VinculacionDto> vinculaciones = miembrosHelper.obtenerVinculacionesDto(Context.of(request));
    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(Context.of(request));
    List<Linea> lineas = RepositorioDeLineas.getInstance().obtenerTodos();
    List<MedioDeTransporte> mediosDeTransporte = RepositorioMediosDeTransporte.getInstance().obtenerTodos();
    Vinculacion vinculacion = request.attribute("vinculacion");
    VinculacionDto vinculacionElegida = determinarVinculacionSeleccionada(vinculaciones, vinculacion);

    ImmutableMap<String, Object> model = ImmutableMap.of(
        "usuario", usuario,
        "miembro", miembro,
        "vinculaciones", vinculaciones,
        "pretramos", TramoDto.ofList(pretramos),
        "lineas", lineas,
        "mediosDeTransporte", mediosDeTransporte,
        "vinculacion", vinculacion,
        "vinculacionElegida", vinculacionElegida,
        "vinculacionesSidebarSelected", false,
        "trayectosSidebarSelected", true
    );
    return new ModelAndView(model, "pages/usuarios/vinculaciones/trayectos/nuevo.html.hbs");
  }
  public ModelAndView nuevoTramoPrivadoParte1(Request request, Response response){
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    Miembro miembro = miembrosHelper.obtenerMiembroDesdeAttributes(Context.of(request)).getValor();
    List<VinculacionDto> vinculaciones = miembrosHelper.obtenerVinculacionesDto(Context.of(request));
    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(Context.of(request));
    List<Linea> lineas = RepositorioDeLineas.getInstance().obtenerTodos();
    List<MedioDeTransporte> mediosDeTransporte = RepositorioMediosDeTransporte.getInstance().obtenerTodos();
    Vinculacion vinculacion = request.attribute("vinculacion");
    VinculacionDto vinculacionElegida = determinarVinculacionSeleccionada(vinculaciones, vinculacion);

    ImmutableMap<String, Object> model = ImmutableMap.of(
            "usuario", usuario,
            "miembro", miembro,
            "vinculaciones", vinculaciones,
            "pretramos", TramoDto.ofList(pretramos),
            "lineas", lineas,
            "mediosDeTransporte", mediosDeTransporte,
            "vinculacion", vinculacion,
            "vinculacionElegida", vinculacionElegida,
            "vinculacionesSidebarSelected", false,
            "trayectosSidebarSelected", true
    );
    return new ModelAndView(model, "pages/usuarios/vinculaciones/trayectos/tramos/nuevo[privado1].html.hbs");
  }
  public ModelAndView nuevoTramoPublicoParte1(Request request, Response response){
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    Miembro miembro = miembrosHelper.obtenerMiembroDesdeAttributes(Context.of(request)).getValor();
    List<VinculacionDto> vinculaciones = miembrosHelper.obtenerVinculacionesDto(Context.of(request));
    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(Context.of(request));
    List<Linea> lineas = RepositorioDeLineas.getInstance().obtenerTodos();
    List<MedioDeTransporte> mediosDeTransporte = RepositorioMediosDeTransporte.getInstance().obtenerTodos();
    Vinculacion vinculacion = request.attribute("vinculacion");
    VinculacionDto vinculacionElegida = determinarVinculacionSeleccionada(vinculaciones, vinculacion);

    ImmutableMap<String, Object> model = ImmutableMap.of(
            "usuario", usuario,
            "miembro", miembro,
            "vinculaciones", vinculaciones,
            "pretramos", TramoDto.ofList(pretramos),
            "lineas", lineas,
            "mediosDeTransporte", mediosDeTransporte,
            "vinculacion", vinculacion,
            "vinculacionElegida", vinculacionElegida,
            "vinculacionesSidebarSelected", false,
            "trayectosSidebarSelected", true
    );
    return new ModelAndView(model, "pages/usuarios/vinculaciones/trayectos/tramos/nuevo[publico1].html.hbs");
  }
  public ModelAndView anadirTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    Miembro miembro = miembrosHelper.obtenerMiembroDesdeAttributes(Context.of(request)).getValor();

    return trayectosHelper.generateTrayecto(Context.of(request), Form.of(request))
      .fold(
        errores -> {
          response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId()
              + "/trayectos/nuevo?errores=" + encode(String.join(", ", errores)));
          return null;
        },
        trayecto -> {
          withTransaction(() -> {
            entityManager().persist(trayecto);
            miembro.darDeAltaTrayecto(trayecto);
            entityManager().merge(miembro);
          });
          tramosHelper.limpiarPretramos(miembro, request);
          response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId() + "/trayectos");
          return null;
        }
      );
  }

  public ModelAndView nuevoTramo(Request request, Response response) {
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    Miembro miembro = miembrosHelper.obtenerMiembroDesdeAttributes(Context.of(request)).getValor();
    List<VinculacionDto> vinculaciones = miembrosHelper.obtenerVinculacionesDto(Context.of(request));
    List<TramoDto> pretramosDtos = TramoDto.ofList(miembrosHelper.obtenerPretramos(Context.of(request)));
    
    Vinculacion vinculacion = request.attribute("vinculacion");
    determinarVinculacionSeleccionada(vinculaciones, vinculacion);

    if (request.queryParams("tipo").equals("publico")) {
      //param de form
      Linea linea = tramosHelper.obtenerLinea(Form.of(request)).getValor();
      
      ImmutableMap<String, Object> model = ImmutableMap.of(
          "usuario", usuario,
          "miembro", miembro,
          "vinculaciones", vinculaciones,
          "pretramos", pretramosDtos,
          "linea", linea,
          "vinculacion", vinculacion,
          "vinculacionesSidebarSelected", false,
          "trayectosSidebarSelected", true
      );
      return new ModelAndView(model, "pages/usuarios/vinculaciones/trayectos/tramos/nuevo[publico].html.hbs");
    }
    else {
      //param de form
      MedioDeTransporte medio = tramosHelper.obtenerMedioDeTransporte(Form.of(request)).getValor();

      ImmutableMap<String, Object> model = ImmutableMap.of(
          "usuario", usuario,
          "miembro", miembro,
          "vinculaciones", vinculaciones,
          "pretramos", pretramosDtos,
          "medio", medio,
          "vinculacion", vinculacion,
          "vinculacionesSidebarSelected", false,
          "trayectosSidebarSelected", true
      );
      return new ModelAndView(model, "pages/usuarios/vinculaciones/trayectos/tramos/nuevo[privado].html.hbs");
    }
  }

  //POST pretramo publico linea
  public ModelAndView tramoPublicoSetLinea(Request request, Response response) {
    Linea linea = tramosHelper.obtenerLinea(Form.of(request)).getValor();
    request.session().attribute("linea", linea);
    response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId() + "/trayectos/nuevo/tramos/nuevo/publico/paradas");
    return null;
  }
  
  //POST pretramo publico paradas
  public ModelAndView tramoPublicoSetParadasYConfirmar(Request request, Response response) {
    Linea linea = request.attribute("linea");

    Long origenID = Form.of(request).getParamOrError("origen", "Es necesario indicar un origen")
        .apply(Long::parseLong, "El id del origen debe ser un numero")
        .getValor();

    Parada origen = linea.getParadas().stream().filter(p -> p.getId().equals(origenID)).findFirst().get();

    Long destinoID = Form.of(request).getParamOrError("destino", "Es necesario indicar un destino")
        .apply(Long::parseLong, "El id del destino debe ser un numero")
        .getValor();

    Parada destino = linea.getParadas().stream().filter(p -> p.getId().equals(destinoID)).findFirst().get();

    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(Context.of(request));
    pretramos.add(new TramoEnTransportePublico(origen, destino, linea));

    response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId() + "/trayectos/nuevo");
    return null;
  }

  //POST pretramo privado medio
  public ModelAndView tramoPrivadoSetMedio(Request request, Response response) {
    MedioDeTransporte medio = tramosHelper.obtenerMedioDeTransporte(Form.of(request)).getValor();
    request.session().attribute("medio", medio);
    response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId() + "/trayectos/nuevo/tramos/nuevo/privado/origen");
    return null;
  }

  //POST pretramo publico origen
  public ModelAndView tramoPrivadoSetOrigen(Request request, Response response) {
    String paisOrigen = Form.of(request).getParamOrError("paisOrigen", "Es necesario indicar un pais de origen").getValor();
    String provinciaOrigen = Form.of(request).getParamOrError("provinciaOrigen", "Es necesario indicar una provincia de origen").getValor();
    String municipioOrigen = Form.of(request).getParamOrError("municipioOrigen", "Es necesario indicar un municipio de origen").getValor();
    String localidadOrigen = Form.of(request).getParamOrError("localidadOrigen", "Es necesario indicar una localidad de origen").getValor();
    String calleOrigen = Form.of(request).getParamOrError("calleOrigen", "Es necesario indicar una calle de origen").getValor();
    String alturaOrigen = Form.of(request).getParamOrError("alturaOrigen", "Es necesario indicar una altura de origen").getValor();

    Ubicacion origen = geolocalizador.getUbicacion(
        paisOrigen,
        provinciaOrigen,
        municipioOrigen,
        localidadOrigen,
        calleOrigen,
        alturaOrigen
    ).get();

    request.session().attribute("origen", origen);
    response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId() + "/trayectos/nuevo/tramos/nuevo/privado/destino");
    return null;
  }

  //POST pretramo publico destino
  public ModelAndView tramoPrivadoSetDestinoYConfirmar(Request request, Response response) {
    String paisDestino = Form.of(request).getParamOrError("paisDestino", "Es necesario indicar un pais de destino").getValor();
    String provinciaDestino = Form.of(request).getParamOrError("provinciaDestino", "Es necesario indicar una provincia de destino").getValor();
    String municipioDestino = Form.of(request).getParamOrError("municipioDestino", "Es necesario indicar un municipio de destino").getValor();
    String localidadDestino = Form.of(request).getParamOrError("localidadDestino", "Es necesario indicar una localidad de destino").getValor();
    String calleDestino = Form.of(request).getParamOrError("calleDestino", "Es necesario indicar una calle de destino").getValor();
    String alturaDestino = Form.of(request).getParamOrError("alturaDestino", "Es necesario indicar una altura de destino").getValor();

    Ubicacion destino = geolocalizador.getUbicacion(
        paisDestino,
        provinciaDestino,
        municipioDestino,
        localidadDestino,
        calleDestino,
        alturaDestino
    ).get();

    Ubicacion origen = request.session().attribute("origen");
    MedioDeTransporte medio = request.session().attribute("medio");

    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(Context.of(request));
    pretramos.add(new TramoPrivado(geolocalizador, origen, destino, medio));

    response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId() + "/trayectos/nuevo/tramos/nuevo");
    return null;
  }

  public ModelAndView anadirTramo(Request request, Response response) {
    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(Context.of(request));

    if (request.queryParams("tipo").equals("publico")) {
      pretramos.add(tramosHelper.generatePreTramoPublico(Form.of(request)));
    } else {
      pretramos.add(tramosHelper.generatePreTramoPrivado(Form.of(request), geolocalizador));
    }

    response.redirect("/usuarios/me/vinculaciones/" + request.<Vinculacion>attribute("vinculacion").getId() + "/trayectos/nuevo");
    return null;
  }
}
