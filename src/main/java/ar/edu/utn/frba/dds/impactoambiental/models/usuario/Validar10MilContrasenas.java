package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import static ar.edu.utn.frba.dds.impactoambiental.ServiceLocator.getServiceLocator;

import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.FormularioLogin;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("10milcontrasenas")
public class Validar10MilContrasenas extends ValidacionDeUsuario {
  @Transient
  private final LectorDeArchivos lectorDeArchivos;

  public Validar10MilContrasenas() {
    this(getServiceLocator().getWeakPasswordsFile());
  }

  public Validar10MilContrasenas(LectorDeArchivos lectorDeArchivos) {
    this.lectorDeArchivos = lectorDeArchivos;
  }


  @Override
  public boolean test(FormularioLogin form) {
    return !lectorDeArchivos.getLineas().contains(form.getContrasena());
  }

  @Override
  public String getMensajeDeError() {
    return "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.";
  }
}
