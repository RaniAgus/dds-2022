package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.dtos.TramoDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TrayectoResumenDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
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

  private Either<Miembro> obtenerMiembro(Request req) {
    return Optional.ofNullable(req.params("miembro"))
        .map(Either::exitoso)
        .orElseGet(() -> Either.fallido("No se especificó un miembro"))
        .apply(Long::parseLong, "El id del miembro debe ser un número")
        .flatMap(req.session().<UsuarioMiembro>attribute("usuario")::getMiembro);
  }

  private List<VinculacionDto> obtenerVinculacionesDto(UsuarioMiembro usuario) {
    List<VinculacionDto> vinculaciones = usuario.getMiembros().stream()
      .map(m -> new VinculacionDto(
        m,
        RepositorioOrganizaciones.getInstance().obtenerTodos().stream()
          .filter(o -> o.getMiembros().contains(m)).findFirst().get(),
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

  private List<Tramo> obtenerPretramos(UsuarioMiembro usuario, Miembro miembro, Request req) {
    Map<Miembro, List<Tramo>> miembrosPretramos = req.session().attribute("pretramos");
    if (miembrosPretramos == null) {
      miembrosPretramos = new HashMap<>();
      req.session().attribute("pretramos", miembrosPretramos);
    }

    return miembrosPretramos.computeIfAbsent(miembro, k -> new ArrayList<>());
  }

  private void limpiarPretramos(UsuarioMiembro usuario, Miembro miembro, Request req) {
    Map<Miembro, List<Tramo>> miembrosPretramos = req.session().attribute("pretramos");
    if (miembrosPretramos != null) {
      miembrosPretramos.remove(miembro);
    }
  }

  public ModelAndView vinculaciones(Request request, Response response) {
    UsuarioMiembro usuario = request.session().<UsuarioMiembro>attribute("usuario");
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", usuario,
      "vinculaciones", vinculaciones
    );

    return new ModelAndView(model, "vinculacionesMiembro.html.hbs");
  }

  public ModelAndView proponerVinculacion(Request request, Response response) {
    UsuarioMiembro usuario = request.session().attribute("usuario");

    return Form.of(request).getParamOrError("codigoInvite", "Es requerido un codigo")
      .apply(UUID::fromString, "El codigo ingresado no tiene el formato correcto")
      .flatMap(sectores::buscarPorCodigoInvite)
      .fold(
        errores -> {
          response.redirect("/miembro/" + usuario.getId() + "/vinculaciones?errores=" + encode(String.join(", ", errores)));
          return null;
        },
        sector -> {
          Miembro membresia = new Miembro(new ArrayList<>());
          sector.solicitarVinculacion(new Vinculacion(membresia));
          usuario.agregarMiembro(membresia);
          response.redirect("/");
          return null;
        }
      );
  }

  public ModelAndView trayectos(Request request, Response response) {
    UsuarioMiembro usuario = request.session().<UsuarioMiembro>attribute("usuario");
    Either<Miembro> miembro = obtenerMiembro(request);
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);

    ImmutableMap<String, Object> model = miembro.fold(
        errors ->(ImmutableMap.of("errores", errors)),
        value ->
            (ImmutableMap.of(
                "usuario", usuario,
                "miembro", miembro,
                "vinculaciones", vinculaciones,
                "trayectos", obtenerTrayectosDto(value))
            )
    );

    return new ModelAndView(model, "trayectosMiembro.html.hbs");
  }

  public ModelAndView nuevoTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = request.session().<UsuarioMiembro>attribute("usuario");
    Either<Miembro> miembro = obtenerMiembro(request);

    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    ImmutableMap<String, Object> model = miembro.fold(
        errors->(ImmutableMap.of("errores",errors)),
        value->(ImmutableMap.<String,Object>of(
            "usuario", usuario,
            "miembro", miembro,
            "vinculaciones", vinculaciones,
            "pretramos", obtenerPretramos(usuario, value, request).stream()
                .map(t -> new TramoDto(t.tipo(), t.nombreMedio(), t.nombreOrigen(), t.nombreDestino()))
                .collect(Collectors.toList())))
    );
    return new ModelAndView(model, "nuevoTrayecto.html.hbs");
  }

  public ModelAndView anadirTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = request.session().<UsuarioMiembro>attribute("usuario");
    Either<Miembro> miembro = obtenerMiembro(request);
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    Either<List<Tramo>> pretramos = miembro.map(valor->obtenerPretramos(usuario,valor, request));

    //Como meto esta validacion sobre una lista fuera del form, con el resto de las validaciones?
    pretramos.map(value-> value.isEmpty()?
        miembro.map(x-> {response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/"
        + x.getId() + "/trayectos/nuevo?errores=El trayecto necesita al menos un tramo"); return value;})
        : value);

    return Form.of(request).getParamOrError("fecha", "Es requerida una fecha")
      .apply(LocalDate::parse, "La fecha ingresada no tiene el formato correcto")
      .fold(
        errores -> { miembro.map(value->{
          response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" + value.getId()
            + "trayectos/nuevo?errores=" + encode(String.join(", ", errores))); return null;});
          return null;
        },
        fecha -> {
          miembro.map(miembro1->{pretramos.map(pretramos1->{miembro1.darDeAltaTrayecto(new Trayecto(fecha, pretramos1)); return pretramos1;});
          limpiarPretramos(usuario, miembro1, request);
          response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" + miembro1.getId() + "/trayectos"); return miembro1;});
          return null;
        }
      );
  }

  public ModelAndView nuevoTramo(Request request, Response response) {
    UsuarioMiembro usuario = request.session().<UsuarioMiembro>attribute("usuario");
    Miembro miembro = obtenerMiembro(request).getValor();
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    List<TramoDto> pretramos = obtenerPretramos(usuario, miembro, request).stream()
      .map(t -> new TramoDto(t.tipo(), t.nombreMedio(), t.nombreOrigen(), t.nombreDestino()))
      .collect(Collectors.toList());

    if (request.queryParams("tipo").equals("publico")) {
      return Form.of(request).getParamOrError("linea", "Es necesario indicar una linea")
        .apply(s -> RepositorioDeLineas.getInstance().obtenerPorID(Long.parseLong(s)).get(), "La linea no existe")
        .fold(
          errores -> {
            response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" + miembro.getId()
              + "/trayectos/nuevo?errores=" + encode(String.join(", ", errores)));
            return null;
          },
          linea -> {
            ImmutableMap<String, Object> model = ImmutableMap.of(
              "usuario", usuario,
              "miembro", miembro,
              "vinculaciones", vinculaciones,
              "pretramos", pretramos,
              "linea", linea
            );

            return new ModelAndView(model, "nuevoTramoPublico.html.hbs");
          }
        );
    }
    else {
      return Form.of(request).getParamOrError("transporte", "Es necesario indicar un medio")
        .apply(s -> RepositorioMediosDeTransporte.getInstance().obtenerPorID(Long.parseLong(s)).get(), "El medio no existe")
        .fold(
          errores -> {
            response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" + miembro.getId()
              + "/trayectos/nuevo?errores=" + encode(String.join(", ", errores)));
            return null;
          },
          medio -> {
            ImmutableMap<String, Object> model = ImmutableMap.of(
              "usuario", usuario,
              "miembro", miembro,
              "vinculaciones", vinculaciones,
              "pretramos", pretramos,
              "medio", medio
            );

            return new ModelAndView(model, "nuevoTramoPrivado.html.hbs");
          }
        );
    }
  }

  public ModelAndView anadirTramo(Request request, Response response) {
    return null;
  }
}
