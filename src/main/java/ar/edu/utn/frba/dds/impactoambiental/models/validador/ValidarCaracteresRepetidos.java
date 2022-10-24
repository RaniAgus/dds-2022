package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import ar.edu.utn.frba.dds.impactoambiental.models.UsuarioDto;
import java.util.regex.Pattern;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteresRepetidos")
public class ValidarCaracteresRepetidos extends Validacion {
  @Override
  public boolean test(UsuarioDto usuarioDto) {
    return !Pattern.compile("(.)\\1{2}").matcher(usuarioDto.getContrasena()).find();
  }

  @Override
  public String getMensajeDeError() {
    return "La contraseña no debe repetir 3 veces seguidas un mismo caracter.";
  }
}
