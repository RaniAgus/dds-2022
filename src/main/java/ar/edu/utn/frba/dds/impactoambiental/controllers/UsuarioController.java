package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Context;
import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.controllers.helpers.UsuariosHelper;
import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionException;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuarioController implements Controller {
  private RepositorioUsuarios usuarios = RepositorioUsuarios.getInstance();
  private UsuariosHelper usuariosHelper = new UsuariosHelper();


  public ModelAndView crearUsuario(Request req, Response res) {
    return usuariosHelper.validarNuevoUsuario(Form.of(req))
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
    if (req.session().attribute("usuarioId") != null) {
      resp.redirect("/");
      return null;
    }

    List<String> errores = Optional.ofNullable(req.queryParams("errores"))
        .map(err -> Arrays.asList(decode(err).split(", ")))
        .orElse(Collections.emptyList());

    return new ModelAndView(ImmutableMap.of("errores", errores,"noHayErrores",errores.isEmpty()), "pages/login/index.html.hbs");
  }

  public Void iniciarSesion(Request req, Response res) {
    return usuariosHelper.obtenerUsuario(Form.of(req))
        .fold(
            errores -> {
              res.redirect("/login?errores=" + encode(String.join(", ", errores)));
              return null;
            },
            usuario -> {
              Context.of(req).setSessionAttribute("usuarioId", usuario.getId());
              res.redirect("/");
              return null;
            }
        );
  }

  public void validarUsuario(Request req, Response res) {
    Context ctx = Context.of(req);
    ctx.<Long>getSessionAttribute("usuarioId", "UNAUTHORIZED")
        .apply(Long::valueOf, "BAD_REQUEST")
        .flatApply(usuarios::obtenerPorID, "UNAUTHORIZED")
        .fold(
            errores -> {
              throw new ValidacionException(errores);
            },
            usuario -> {
              ctx.setRequestAttribute("usuario", usuario);
              return null;
            }
        );
  }

  public ModelAndView cerrarSesion(Request request, Response response) {
    request.session().removeAttribute("usuarioId");
    response.redirect("/");
    return null;
  }
}
