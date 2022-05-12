package models;

import models.exceptions.ContrasenaDebilException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarCaracteresRepetidos implements Validacion{
  @Override
  public Optional<String> validar(String Usuario,String contrasena) {
    Optional<String> error = Optional.empty();
    Pattern patronRepetitive = Pattern.compile("(.)\\1{2}");
    Matcher matcherRepetittive = patronRepetitive.matcher(contrasena);
    if (matcherRepetittive.find()) {
      error = Optional.of("La contraseña no debe repetir 3 veces seguidas.");
    }
    return error;
  }
}
