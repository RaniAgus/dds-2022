package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import java.util.Optional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteresConsecutivos")
public class ValidarCaracteresConsecutivos extends Validacion {
  @Override
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    char[] contrasenaArray = contrasena.toCharArray();
    for (int i = 0; i < contrasenaArray.length - 3; i++) {
      if ((contrasenaArray[i] == contrasenaArray[i + 1] - 1
          && contrasenaArray[i] == contrasenaArray[i + 2] - 2
          && contrasenaArray[i] == contrasenaArray[i + 3] - 3)
          || (contrasenaArray[i] == contrasenaArray[i + 1] + 1
          && contrasenaArray[i] == contrasenaArray[i + 2] + 2
          && contrasenaArray[i] == contrasenaArray[i + 3] + 3)) {
        return Optional.of("La contraseña no debe tener 4 caracteres consecutivos.");
      }
    }
    return error;
  }
}
