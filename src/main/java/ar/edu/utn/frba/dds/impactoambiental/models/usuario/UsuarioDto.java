package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;

public class UsuarioDto {
  private String username;
  private String password;

  public UsuarioDto(String usuario, String password) {
    this.username = usuario;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public static Either<UsuarioDto> parsear(Form form) {
    Either<String> username = form.getParamOrError("username", "El usuario es requerido");
    Either<String> password = form.getParamOrError("password", "La contraseña es requerida");

    return Either.concatenar(
        () -> new UsuarioDto(username.getValor(), password.getValor()),
        username, password);
  }
}
