package models.validador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import exceptions.ContrasenaDebilException;

public class Validador {
  private List<Validacion> validaciones;

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