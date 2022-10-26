package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import java.util.Optional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("usuarioPorDefecto")
public class ValidarUsuarioPorDefecto extends Validacion {
  @Override
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    if (usuario.equals(contrasena)) {
      error = Optional.of("No se puede utilizar contraseñas por defecto.");
    }
    return error;
  }
}
