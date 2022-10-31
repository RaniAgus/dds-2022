package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("usuarioPorDefecto")
public class ValidarUsuarioPorDefecto extends ValidacionDeUsuario {

  @Override
  public boolean test(UsuarioDto dto) {
    return !dto.getUsuario().equals(dto.getContrasena());
  }

  @Override
  public String getMensajeDeError() {
    return "No se puede utilizar contraseñas por defecto.";
  }
}
