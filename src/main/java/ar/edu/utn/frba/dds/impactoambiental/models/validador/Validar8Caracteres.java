package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import ar.edu.utn.frba.dds.impactoambiental.models.UsuarioDto;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("8caracteres")
public class Validar8Caracteres extends Validacion {
  @Override
  public boolean test(UsuarioDto usuarioDto) {
    return usuarioDto.getContrasena().length() >= 8;
  }

  @Override
  public String getMensajeDeError() {
    return "La contraseña debe tener al menos 8 caracteres.";
  }
}
