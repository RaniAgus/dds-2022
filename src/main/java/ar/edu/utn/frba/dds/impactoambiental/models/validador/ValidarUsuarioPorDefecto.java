package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import ar.edu.utn.frba.dds.impactoambiental.models.UsuarioDto;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("usuarioPorDefecto")
public class ValidarUsuarioPorDefecto extends Validacion {
  @Override
  public boolean test(UsuarioDto usuarioDto) {
    return !usuarioDto.getUsuario().equals(usuarioDto.getContrasena());
  }

  @Override
  public String getMensajeDeError() {
    return "No se puede utilizar contraseñas por defecto.";
  }
}
