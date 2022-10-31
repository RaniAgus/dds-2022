package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import java.util.regex.Pattern;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteresRepetidos")
public class ValidarCaracteresRepetidos extends ValidacionDeUsuario {

  @Override
  public boolean test(UsuarioDto dto) {
    return !Pattern.compile("(.)\\1{2}").matcher(dto.getPassword()).find();
  }

  @Override
  public String getMensajeDeError() {
    return "La contraseña no debe repetir 3 veces seguidas un mismo caracter.";
  }
}
