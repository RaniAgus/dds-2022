package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import static ar.edu.utn.frba.dds.impactoambiental.ServiceLocator.getServiceLocator;

import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import java.util.Optional;
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
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    if (lectorDeArchivos.getLineas().contains(contrasena)) {
      error = Optional.of("Contraseña dentro de las 10000 mas usadas. Elija otra por favor.");
    }
    return error;
  }
}

