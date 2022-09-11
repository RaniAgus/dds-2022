package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import java.util.Optional;

public class ValidarUsuarioPorDefecto implements Validacion {
  @Override
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    if (usuario.equals(contrasena)) {
      error = Optional.of("No se puede utilizar contraseñas por defecto.");
    }
    return error;
  }
}
