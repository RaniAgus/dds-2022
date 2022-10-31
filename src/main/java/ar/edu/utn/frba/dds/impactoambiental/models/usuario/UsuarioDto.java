package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;

public class UsuarioDto {
  private String usuario;
  private String contrasena;

  public UsuarioDto(String usuario, String contrasena) {
    this.usuario = usuario;
    this.contrasena = contrasena;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public static Either<UsuarioDto> parsear(Form form) {
    Either<String> usuario = form.getParamOrError("usuario", "El usuario es requerido");
    Either<String> contrasena = form.getParamOrError("contrasena", "La contraseña es requerida");

    return Either.concatenar(() -> new UsuarioDto(usuario.getValor(), contrasena.getValor()), usuario, contrasena);
  }
}
