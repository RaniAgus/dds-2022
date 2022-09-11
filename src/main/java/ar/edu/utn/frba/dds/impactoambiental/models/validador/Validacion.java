package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import java.util.Optional;

public interface Validacion {
  Optional<String> validar(String usuario, String contrasena);
}
