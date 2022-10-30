package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.function.Predicate;

@Entity
@DiscriminatorValue("usuarioPorDefecto")
public class ValidarUsuarioPorDefecto extends ValidacionDeUsuario {

  @Override
  public Predicate<UsuarioDto> validationCondition(UsuarioDto usuarioDto) {
    return (usuarioDto1)->!usuarioDto.getUsuario().equals(usuarioDto.getContrasena());
  }

  @Override
  public Predicate<UsuarioDto> validationCondition() {
    return (usuarioDto1)->!usuarioDto1.getUsuario().equals(usuarioDto1.getContrasena());
  }

  @Override
  public String getMensajeDeError() {
    return "No se puede utilizar contraseñas por defecto.";
  }

}
