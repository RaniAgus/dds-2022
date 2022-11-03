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
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
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

public class MiembroController implements Controller {
  private RepositorioDeSectores sectores = RepositorioDeSectores.getInstance();
  private RepositorioOrganizaciones organizaciones = RepositorioOrganizaciones.getInstance();
  private RepositorioVinculaciones vinculaciones = RepositorioVinculaciones.getInstance();
  private Geolocalizador geolocalizador = new Geolocalizador("Bearer /deHQgNGwBMcTx2fwx0P0xnoPvqSJzSb6/+8Bg0OC7g=");// TODO: turbio el apikey en el servicelocator porque no una variable de entorno??
  private TramosHelper tramosHelper = new TramosHelper();
  private MiembrosHelper miembrosHelper = new MiembrosHelper();
  private TrayectosHelper trayectosHelper = new TrayectosHelper();

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
      "vinculaciones", vinculaciones
    );
    return new ModelAndView(model, "vinculacionesMiembro.html.hbs");
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

    ImmutableMap<String, Object> model = ImmutableMap.of(
        "usuario", usuario,
        "miembro", miembro,
        "vinculaciones", vinculaciones,
        "trayectos", trayectos,
        "vinculacion", request.attribute("vinculacion")
    );
    return new ModelAndView(model, "trayectosMiembro.html.hbs");
  }

  public ModelAndView nuevoTrayecto(Request request, Response response) {
    UsuarioMiembro usuario = miembrosHelper.usuarioMiembroDeSesion(Context.of(request)).getValor();
    Miembro miembro = miembrosHelper.obtenerMiembroDesdeAttributes(Context.of(request)).getValor();
    List<VinculacionDto> vinculaciones = miembrosHelper.obtenerVinculacionesDto(Context.of(request));
    List<Tramo> pretramos = miembrosHelper.obtenerPretramos(Context.of(request));
    List<Linea> lineas = RepositorioDeLineas.getInstance().obtenerTodos();
    List<MedioDeTransporte> mediosDeTransporte = RepositorioMediosDeTransporte.getInstance().obtenerTodos();

    ImmutableMap<String, Object> model = ImmutableMap.of(
        "usuario", usuario,
        "miembro", miembro,
        "vinculaciones", vinculaciones,
        "pretramos", TramoDto.ofList(pretramos),
        "lineas", lineas,
        "mediosDeTransporte", mediosDeTransporte,
        "vinculacion", request.attribute("vinculacion")
    );
    return new ModelAndView(model, "nuevoTrayecto.html.hbs");
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

    if (request.queryParams("tipo").equals("publico")) {
      //param de form
      Linea linea = tramosHelper.obtenerLinea(Form.of(request)).getValor();
      
      ImmutableMap<String, Object> model = ImmutableMap.of(
          "usuario", usuario,
          "miembro", miembro,
          "vinculaciones", vinculaciones,
          "pretramos", pretramosDtos,
          "linea", linea,
          "vinculacion", request.attribute("vinculacion")
      );
      return new ModelAndView(model, "nuevoTramoPublico.html.hbs");
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
          "vinculacion", request.attribute("vinculacion")
      );
      return new ModelAndView(model, "nuevoTramoPrivado.html.hbs");
    }
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
