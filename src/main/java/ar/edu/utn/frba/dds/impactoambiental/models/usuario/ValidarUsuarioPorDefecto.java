package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.FormularioLogin;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("usuarioPorDefecto")
public class ValidarUsuarioPorDefecto extends ValidacionDeUsuario {

  @Override
  public boolean test(FormularioLogin form) {
    return !form.getUsuario().equals(form.getContrasena());
  }

  @Override
  public String getMensajeDeError() {
    return "No se puede utilizar contraseñas por defecto.";
  }
}
