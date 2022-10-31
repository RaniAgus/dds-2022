package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioValidacionesDeUsuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    if (req.session().attribute("usuario") != null) {
      resp.redirect("/");
      return null;
    }

    List<String> errores = Arrays.asList(decode(req.queryParams("errores")).split(", "));
    System.out.println(errores);

    return new ModelAndView(null, "login.html.hbs");
  }

  public Void iniciarSesion(Request req, Response res) {
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
              req.session().attribute("usuario", usuario);
              res.redirect("/");
              return null;
            }
        );
  }

  public ModelAndView cerrarSesion(Request request, Response response) {
    request.session().removeAttribute("usuario");
    response.redirect("/");
    return null;
  }
}
