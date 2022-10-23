package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ContrasenaDebilException;
import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Validador extends EntidadPersistente {
  @ManyToMany
  private List<Validacion> validaciones;

  protected Validador() {}

  public Validador(List<Validacion> validaciones) {
    this.validaciones = validaciones;
  }

  public void agregarValidacion(Validacion validacion) {
    this.validaciones.add(validacion);
  }

  public void validar(String usuario, String contrasena) {
    String errores = validaciones.stream()
        .map(validacion -> validacion.validar(usuario, contrasena))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.joining());

    if (!errores.isEmpty()) {
      throw new ContrasenaDebilException(errores);
    }
  }
}
