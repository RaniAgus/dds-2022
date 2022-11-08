package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import static ar.edu.utn.frba.dds.impactoambiental.ServiceLocator.getServiceLocator;

import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Lector;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("10milcontrasenas")
public class Validar10MilContrasenas extends ValidacionDeUsuario {
  @Transient
  private final Lector lector;

  public Validar10MilContrasenas() {
    lector = getServiceLocator().getWeakPasswordsFileReader();
  }

  public Validar10MilContrasenas(Lector lector) {
    this.lector = lector;
  }

  @Override
  public boolean test(UsuarioDto dto) {
    return !lector.getLineas().contains(dto.getPassword());
  }

  @Override
  public String getMensajeDeError() {
    return "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.";
  }
}
