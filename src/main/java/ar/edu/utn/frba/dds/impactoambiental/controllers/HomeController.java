package ar.edu.utn.frba.dds.impactoambiental.controllers;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

  public ModelAndView recomendaciones(Request req, Response resp) {
    return new ModelAndView(null, "recomendaciones.html.hbs");
  }

  public Void home(Request req, Response resp) {
    Usuario usuario = req.session().attribute("usuario");
    if (usuario == null) {
      resp.redirect("/recomendaciones");
    } else {
      resp.redirect(usuario.getHomeUrl());
    }
    return null;
  }
}
