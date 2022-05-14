package models.exceptions;

import java.util.function.Supplier;

public class UsuarioNoDisponibleExeption extends RuntimeException {
  public UsuarioNoDisponibleExeption(String message) {
    super(message);
  }
}
