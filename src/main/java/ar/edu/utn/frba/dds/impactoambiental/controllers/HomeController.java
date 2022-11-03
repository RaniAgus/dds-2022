package ar.edu.utn.frba.dds.impactoambiental.controllers;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import java.util.Optional;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController implements Controller {
  private RepositorioUsuarios usuarios = RepositorioUsuarios.getInstance();

  public ModelAndView recomendaciones(Request req, Response resp) {
    return new ModelAndView(null, "recomendaciones.html.hbs");
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
