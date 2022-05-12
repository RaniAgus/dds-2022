package models;

import models.exceptions.ContrasenaDebilException;

import java.util.Optional;

public class ValidarCaracteresConsecutivos implements Validacion{
  @Override
  public Optional<String> validar(String Usuario,String contrasena) {
    Optional<String> error = Optional.empty();
    char [] contrasenaArray = contrasena.toCharArray();
    for (int i = 0; i < contrasenaArray.length - 3; i++) {
      if ((contrasenaArray[i] == contrasenaArray[i + 1] - 1
          && contrasenaArray[i] == contrasenaArray[i + 2] - 2
          && contrasenaArray[i] == contrasenaArray[i + 3] - 3)
          || (contrasenaArray[i] == contrasenaArray[i + 1] + 1
          && contrasenaArray[i] == contrasenaArray[i + 2] + 2
          && contrasenaArray[i] == contrasenaArray[i + 3] + 3)) {
        error = Optional.of("La contraseña no debe tener 4 caracteres consecutivos");
      }
    }
    return error;
  }
}
