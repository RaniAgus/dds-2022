package ar.edu.utn.frba.dds.impactoambiental.controllers;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;


public class HomeController {

  public ModelAndView recomendaciones(Request req, Response resp) {
    return new ModelAndView(null, "recomendaciones.html.hbs");
  }

  public ModelAndView login(Request req, Response resp) {
    if (req.cookie("SESSIONID") != null) {
      resp.redirect("/");
      return null;
    }
    return new ModelAndView(null, "login.html.hbs");
  }

  public ModelAndView home(Request req, Response resp) {
    resp.redirect("/recomendaciones");
    return null;
  }

  public ModelAndView validarLogin(Request req, Response resp) {
    // TODO
    return null;
  }

  public ModelAndView cerrarSesion(Request request, Response response) {
    response.removeCookie("SESSIONID");
    response.redirect("/");
    return null;
    // No se que tan bien esta esto la verdad
  }
}
