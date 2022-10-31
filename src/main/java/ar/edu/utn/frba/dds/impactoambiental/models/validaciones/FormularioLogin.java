package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import java.util.List;
import spark.Request;

public class FormularioLogin {
  private String usuario;
  private String contrasena;

  public FormularioLogin(String usuario, String contrasena) {
    this.usuario = usuario;
    this.contrasena = contrasena;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public static Either<FormularioLogin> parsear(Request req) {
    Form form = Form.of(req);

    Either<String> usuario = form.getParamOrError("usuario", "El usuario es requerido");
    Either<String> contrasena = form.getParamOrError("contrasena", "La contraseña es requerida");

    List<String> errores = Either.colectarErrores(usuario, contrasena);
    return errores.isEmpty()
        ? Either.exitoso(new FormularioLogin(usuario.getValor(), contrasena.getValor()))
        : Either.fallido(errores);
  }
}
