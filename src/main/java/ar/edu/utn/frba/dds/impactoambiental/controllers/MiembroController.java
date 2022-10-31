package ar.edu.utn.frba.dds.impactoambiental.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MiembroController implements Controlador{

  //Esto esta feo pero es un lindo placeholder
  public UsuarioMiembro obtenerUsuario(Request req) {
    return (UsuarioMiembro) RepositorioUsuarios.getInstance().obtenerPorID(req.session().attribute("usuario")).get();
  }

  public ModelAndView vinculaciones(Request request, Response response) {
    UsuarioMiembro usuario = obtenerUsuario(request);

    List<VinculacionDto> vinculaciones = usuario.getMiembros().stream()
      .map(m -> new VinculacionDto(
        m,
        RepositorioOrganizaciones.getInstance().obtenerTodos().stream()
          .filter(o -> o.getMiembros().contains(m)).findFirst().get(),
        null
      )).collect(Collectors.toList());

    vinculaciones.forEach(v -> v.setSector(
      v.getOrganizacion().getSectores().stream()
        .filter(s -> s.getMiembros().contains(v.getMiembro()))
        .findFirst().get()
    ));

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
    
    return null;
  }

  public ModelAndView nuevoTrayecto(Request request, Response response) {
    return null;
  }

  public ModelAndView anadirTrayecto(Request request, Response response) {
    return null;
  }

  public ModelAndView nuevoTramo(Request request, Response response) {
    return null;
  }

  public ModelAndView anadirTramo(Request request, Response response) {
    return null;
  }
}
