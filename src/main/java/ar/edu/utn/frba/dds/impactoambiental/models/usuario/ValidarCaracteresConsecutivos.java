package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteresConsecutivos")
public class ValidarCaracteresConsecutivos extends ValidacionDeUsuario {

  public boolean test(UsuarioDto usuarioDto) {
    char[] contrasenaArray = usuarioDto.getContrasena().toCharArray();
    for (int i = 0; i < contrasenaArray.length - 3; i++) {
      if ((contrasenaArray[i] == contrasenaArray[i + 1] - 1
          && contrasenaArray[i] == contrasenaArray[i + 2] - 2
          && contrasenaArray[i] == contrasenaArray[i + 3] - 3)
          || (contrasenaArray[i] == contrasenaArray[i + 1] + 1
          && contrasenaArray[i] == contrasenaArray[i + 2] + 2
          && contrasenaArray[i] == contrasenaArray[i + 3] + 3)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getMensajeDeError() {
    return "La contraseña no debe tener 4 caracteres consecutivos.";
  }
}
