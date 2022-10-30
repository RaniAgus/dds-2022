package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteresRepetidos")
public class ValidarCaracteresRepetidos extends ValidacionDeUsuario {


  public boolean predicater(UsuarioDto usuarioDto) {
    return !Pattern.compile("(.)\\1{2}").matcher(usuarioDto.getContrasena()).find();
  }

  @Override
  public Predicate<UsuarioDto> validationCondition() {
    return this::predicater;
  }

  @Override
  public String getMensajeDeError() {
    return "La contraseña no debe repetir 3 veces seguidas un mismo caracter.";
  }

  @Override
  public Predicate<UsuarioDto> validationCondition(UsuarioDto usuarioDto) {
    return (usuarioDto1 -> predicater(usuarioDto));
  }
}
