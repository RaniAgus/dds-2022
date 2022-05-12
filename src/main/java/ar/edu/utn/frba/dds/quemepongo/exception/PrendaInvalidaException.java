package ar.edu.utn.frba.dds.quemepongo.exception;

import ar.edu.utn.frba.dds.quemepongo.model.Material;
import ar.edu.utn.frba.dds.quemepongo.model.Tipo;

import static java.lang.String.format;

public class PrendaInvalidaException extends RuntimeException {
  public PrendaInvalidaException(String s) {
    super("Falta parámetro: " + s);
  }

  public PrendaInvalidaException(Tipo tipo, Material material) {
    super(format("Material no válido para %s: %s", tipo, material));
  }
}
