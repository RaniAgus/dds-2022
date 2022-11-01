package ar.edu.utn.frba.dds.impactoambiental.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TramoDto;
import ar.edu.utn.frba.dds.impactoambiental.dtos.TrayectoResumenDto;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeLineas;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioMediosDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MiembroController implements Controlador{

  //Asume que no hay errores
  public UsuarioMiembro obtenerUsuario(Request req) {
    return (UsuarioMiembro) RepositorioUsuarios.getInstance()
      .obtenerPorID(req.session().attribute("usuario")).get();
  }

  //Asume que no hay errores
  public Miembro obtenerMiembro(Request req) {
    return obtenerUsuario(req).getMiembros().stream()
      .filter(m -> m.getId() == Long.parseLong(req.params("miembro")))
      .findFirst().get();
  }

  public List<VinculacionDto> obtenerVinculacionesDto(UsuarioMiembro usuario) {
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

  public List<TrayectoResumenDto> obtenerTrayectosDto(Miembro miembro) {
    return miembro.getTrayectos().stream()
      .map(t -> new TrayectoResumenDto(
        t.getFecha(),
        t.getCodigoInvite(),
        t.getTramos().get(0).nombreOrigen(),
        t.getTramos().get(t.getTramos().size() - 1).nombreDestino()
      ))
      .collect(Collectors.toList());
  }

  public List<Tramo> obtenerPretramos(UsuarioMiembro usuario, Miembro miembro, Request req) {
    Map<Miembro, List<Tramo>> miembrosPretramos = req.session().attribute("pretramos");
    if (miembrosPretramos == null) {
      miembrosPretramos = new HashMap<>();
      req.session().attribute("pretramos", miembrosPretramos);
    }

    return miembrosPretramos.computeIfAbsent(miembro, k -> new ArrayList<>());
  }

  public void limpiarPretramos(UsuarioMiembro usuario, Miembro miembro, Request req) {
    Map<Miembro, List<Tramo>> miembrosPretramos = req.session().attribute("pretramos");
    if (miembrosPretramos != null) {
      miembrosPretramos.remove(miembro);
    }
  }

  public ModelAndView vinculaciones(Request request, Response response) {
    UsuarioMiembro usuario = obtenerUsuario(request);

    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", usuario,
      "vinculaciones", vinculaciones
    );

    return new ModelAndView(model, "vinculacionesMiembro.html.hbs");
  }

  public ModelAndView proponerVinculacion(Request request, Response response) {
    UsuarioMiembro usuario = obtenerUsuario(request);

    return Form.of(request).getParamOrError("codigoInvite", "Es requerido un codigo")
      .map(UUID::fromString, "El codigo ingresado no tiene el formato correcto")
      .map(u -> RepositorioOrganizaciones.getInstance().obtenerTodos().stream()
        .flatMap(o -> o.getSectores().stream()
          .filter(s -> s.getCodigoInvite().equals(u))).findFirst().get()
        , "El codigo ingresado no existe")
      .fold(
        errores -> {
          response.redirect("/miembro/" + usuario.getId() + "/vinculaciones?errores=" + encode(String.join(", ", errores)));
          return null;
        },
        sector -> {
          Miembro membersia = new Miembro(new ArrayList<>());
          sector.solicitarVinculacion(new Vinculacion(membersia));
          usuario.agregarMiembro(membersia);
    
          response.redirect("/");
          return null;
        }
      );
  }

  public ModelAndView trayectos(Request request, Response response) {
    UsuarioMiembro usuario = obtenerUsuario(request);
    Miembro miembro = obtenerMiembro(request);
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
    UsuarioMiembro usuario = obtenerUsuario(request);
    Miembro miembro = obtenerMiembro(request);
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    List<TramoDto> pretramos = obtenerPretramos(usuario, miembro, request).stream()
      .map(t -> new TramoDto(t.tipo(), t.nombreMedio(), t.nombreOrigen(), t.nombreDestino()))
      .collect(Collectors.toList());

    ImmutableMap<String, Object> model = ImmutableMap.of(
      "usuario", usuario,
      "miembro", miembro,
      "vinculaciones", vinculaciones,
      "pretramos", pretramos
    );

    return new ModelAndView(model, "nuevoTrayecto.html.hbs");
  }

  public ModelAndView anadirTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = obtenerUsuario(request);
    Miembro miembro = obtenerMiembro(request);
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    List<Tramo> pretramos = obtenerPretramos(usuario, miembro, request);
    
    //Como meto esta validacion sobre una lista fuera del form, con el resto de las validaciones?
    if(pretramos.isEmpty()) {
      response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" 
        + miembro.getId() + "/trayectos/nuevo?errores=El trayecto necesita al menos un tramo");
      return null;
    }

    return Form.of(request).getParamOrError("fecha", "Es requerida una fecha")
      .map(LocalDate::parse, "La fecha ingresada no tiene el formato correcto")
      .fold(
        errores -> {
          response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" + miembro.getId()
            + "trayectos/nuevo?errores=" + encode(String.join(", ", errores)));
          return null;
        },
        fecha -> {
          miembro.darDeAltaTrayecto(new Trayecto(fecha, pretramos));
          limpiarPretramos(usuario, miembro, request);

          response.redirect("/miembro/" + usuario.getId() + "/vinculaciones/" + miembro.getId() + "/trayectos");
          return null;
        }
      );
  }

  public ModelAndView nuevoTramo(Request request, Response response) {
    UsuarioMiembro usuario = obtenerUsuario(request);
    Miembro miembro = obtenerMiembro(request);
    List<VinculacionDto> vinculaciones = obtenerVinculacionesDto(usuario);
    List<TramoDto> pretramos = obtenerPretramos(usuario, miembro, request).stream()
      .map(t -> new TramoDto(t.tipo(), t.nombreMedio(), t.nombreOrigen(), t.nombreDestino()))
      .collect(Collectors.toList());

    if (request.queryParams("tipo").equals("publico")) {
      return Form.of(request).getParamOrError("linea", "Es necesario indicar una linea")
        .map(s -> RepositorioDeLineas.getInstance().obtenerPorID(Long.parseLong(s)).get(), "La linea no existe")
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
        .map(s -> RepositorioMediosDeTransporte.getInstance().obtenerPorID(Long.parseLong(s)).get(), "El medio no existe")
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
