package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("8caracteres")
public class Validar8Caracteres extends ValidacionDeUsuario {

  @Override
  public boolean test(UsuarioDto usuarioDto) {
    return usuarioDto.getContrasena().length() >= 8;
  }

  public String getMensajeDeError() {
    return "La contraseña debe tener al menos 8 caracteres.";
  }
}
