package models.validador;

import models.da.LectorDeArchivos;

import java.util.Optional;

public class Validar10MilContrasenas implements Validacion {
  private final LectorDeArchivos lectorDeArchivos;

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

