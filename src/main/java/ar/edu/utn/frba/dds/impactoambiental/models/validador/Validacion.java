package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Validacion extends EntidadPersistente {
  public abstract Optional<String> validar(String usuario, String contrasena);
}
