package models.validador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Validar10MilContrasenas implements Validacion {
  private final List<String> contrasenasProhibidas = new ArrayList<String>();

  public Validar10MilContrasenas() throws FileNotFoundException {
    File listado = new File("./Weak_Paswords.txt");
    Scanner contrasenasDebiles = new Scanner(listado);
    while (contrasenasDebiles.hasNextLine()) {
      this.contrasenasProhibidas.add(contrasenasDebiles.nextLine());
    }
    contrasenasDebiles.close();
  }

  @Override
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    if (this.contrasenasProhibidas.contains(contrasena)) {
      error = Optional.of("Contraseña dentro de las 10000 mas usadas. Elija otra por favor.");
    }
    return error;
  }
}

