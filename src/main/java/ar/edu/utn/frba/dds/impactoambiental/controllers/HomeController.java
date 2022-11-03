package ar.edu.utn.frba.dds.impactoambiental.controllers;
import ar.edu.utn.frba.dds.impactoambiental.dtos.RecomendacionDto;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Recomendacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioDeRecomendaciones;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController implements Controller {
  private RepositorioUsuarios usuarios = RepositorioUsuarios.getInstance();

  public ModelAndView recomendaciones(Request req, Response resp) {
    List<Recomendacion> recomendaciones = RepositorioDeRecomendaciones.getInstance().obtenerLasDiezMasCercanas();
    Map<String, Object> models= ImmutableMap.of("recomendaciones",recomendaciones.stream().map(RecomendacionDto::fromRecomendacion).collect(Collectors.toList()));

    return new ModelAndView(models, "pages/Recomendaciones/index.html.hbs");
  }

  public Void home(Request req, Response resp) {
    Usuario usuario = Optional.ofNullable(req.session().<Long>attribute("usuarioId"))
        .flatMap(usuarios::obtenerPorID)
        .orElse(null);

    if (usuario == null) {
      resp.redirect("/recomendaciones");
    } else {
      resp.redirect(usuario.getHomeUrl());
    }

    return null;
  }
}
