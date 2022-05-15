package models.validador;

import java.util.Optional;

public class Validar8Caracteres implements Validacion {
  @Override
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    if (contrasena.length() < 8) {
      error = Optional.of("La contraseña debe tener al menos 8 caracteres.");
    }
    return error;
  }
}