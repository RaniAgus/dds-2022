package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteresRepetidos")
public class ValidarCaracteresRepetidos extends Validacion {
  @Override
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    Pattern patronRepetitive = Pattern.compile("(.)\\1{2}");
    Matcher matcherRepetittive = patronRepetitive.matcher(contrasena);
    if (matcherRepetittive.find()) {
      error = Optional.of("La contraseña no debe repetir 3 veces seguidas un mismo caracter.");
    }
    return error;
  }
}
