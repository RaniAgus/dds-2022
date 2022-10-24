package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import static ar.edu.utn.frba.dds.impactoambiental.ServiceLocator.getServiceLocator;

import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("10milcontrasenas")
public class Validar10MilContrasenas extends Validacion {
  @Transient
  private final LectorDeArchivos lectorDeArchivos;

  protected Validar10MilContrasenas() {
    this(getServiceLocator().getWeakPasswordsFile());
  }

  public Validar10MilContrasenas(LectorDeArchivos lectorDeArchivos) {
    this.lectorDeArchivos = lectorDeArchivos;
  }

  @Override
  public boolean test(UsuarioDto usuarioDto) {
    return !lectorDeArchivos.getLineas().contains(usuarioDto.getContrasena());
  }

  @Override
  public String getMensajeDeError() {
    return "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.";
  }
}
