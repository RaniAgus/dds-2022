package ar.edu.utn.frba.dds.impactoambiental.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import ar.edu.utn.frba.dds.impactoambiental.dtos.VinculacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MiembroController {
  public ModelAndView vinculaciones(Request request, Response response) {
    UsuarioMiembro usuario = RepositorioUsuarios.getInstance()
      .obtenerPorID(request.session().attribute("usuario_id")).get(); 
    //ver como solucionar casteo usuario -> usuarioMiembro

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
    return null;
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
