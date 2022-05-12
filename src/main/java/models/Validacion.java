package models;

import java.util.Optional;

public interface Validacion {
  public Optional<String> validar(String usuario,String contrasena);
}
