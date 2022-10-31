package ar.edu.utn.frba.dds.impactoambiental.controllers;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

  public ModelAndView recomendaciones(Request req, Response resp) {
    return new ModelAndView(null, "recomendaciones.html.hbs");
  }

  public ModelAndView home(Request req, Response resp) {
    resp.redirect("/recomendaciones");
    return null;
  }
}
