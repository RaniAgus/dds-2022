package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioValidacionesDeUsuario;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuarioController implements Controlador {
  private RepositorioUsuarios usuarios = RepositorioUsuarios.getInstance();

  private RepositorioValidacionesDeUsuario validaciones = RepositorioValidacionesDeUsuario.getInstance();

  public ModelAndView crearUsuario(Request req, Response res) {
    return UsuarioDto.parsear(Form.of(req))
        .flatMap(dto -> new Validador<>(dto)
            .agregarValidaciones(new ArrayList<>(validaciones.obtenerTodos()))
            .validar())
        .fold(
            errores -> {
              errores.isEmpty();
              // TODO: Manejo de errores
              return null;
            },
            dto -> {
              // TODO: Crear usuario
              return null;
            }
        );
  }

  public ModelAndView verLogin(Request req, Response resp) {
    if (req.cookie("SESSIONID") != null) {
      resp.redirect("/");
      return null;
    }
    System.out.println(decode(req.queryParams("errores")));
    return new ModelAndView(null, "login.html.hbs");
  }

  public ModelAndView iniciarSesion(Request req, Response res) {
    return UsuarioDto.parsear(Form.of(req))
        .flatMap(dto -> usuarios.obtenerUsuario(
            dto.getUsuario(),
            dto.getContrasena()))
        .fold(
            errores -> {
              res.redirect("/login?errores=" + encode(String.join(", ", errores)));
              return null;
            },
            usuario -> {
              usuario.getUsuario();
              usuario.getContrasena();
              // TODO: Loguear usuario
              return null;
            }
        );
  }

  public ModelAndView cerrarSesion(Request request, Response response) {
    response.removeCookie("SESSIONID");
    response.redirect("/");
    return null;
    // No se que tan bien esta esto la verdad
  }
}
