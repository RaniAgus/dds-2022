package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import io.vavr.control.Either;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.function.Predicate;

@Entity
@DiscriminatorValue("8caracteres")
public class Validar8Caracteres extends ValidacionDeUsuario {

  @Override
  public Predicate<UsuarioDto> validationCondition() {
    return (usuarioDto)->usuarioDto.getContrasena().length() >= 8;
  }

  public String getMensajeDeError() {
    return "La contraseña debe tener al menos 8 caracteres.";
  }

  @Override
  public Predicate<UsuarioDto> validationCondition(UsuarioDto usuarioDto) {
    return (usuarioDto1)->usuarioDto.getContrasena().length() >=8 ;
  }
}
