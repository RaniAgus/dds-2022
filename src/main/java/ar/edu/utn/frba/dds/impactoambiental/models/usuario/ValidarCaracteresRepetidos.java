package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.FormularioLogin;
import java.util.regex.Pattern;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteresRepetidos")
public class ValidarCaracteresRepetidos extends ValidacionDeUsuario {

  @Override
  public boolean test(FormularioLogin form) {
    return !Pattern.compile("(.)\\1{2}").matcher(form.getContrasena()).find();
  }

  @Override
  public String getMensajeDeError() {
    return "La contraseña no debe repetir 3 veces seguidas un mismo caracter.";
  }
}
