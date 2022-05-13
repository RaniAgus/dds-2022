package models;

import models.exceptions.ContrasenaDebilException;

import java.util.List;
import java.util.Optional;

public class Validador {
  List<Validacion> validaciones;

  public Validador(List<Validacion> validaciones) {
    this.validaciones = validaciones;
  }

  public void agregarValidacion(Validacion validacion) {
    this.validaciones.add(validacion);
  }
  
  public void validar(String usuario,String contrasena) {
    String errores = validaciones.stream().reduce("", (errorsAccumulator, currentValidacion) -> {
    Optional<String> error = currentValidacion.validar(usuario,contrasena);
    errorsAccumulator += error.orElse("");
      return errorsAccumulator;
    }, String :: concat);
    if (!errores.isEmpty()){
      throw new ContrasenaDebilException(errores);
    }else{
      System.out.println("Contraseña pasada");
    }
  }
}