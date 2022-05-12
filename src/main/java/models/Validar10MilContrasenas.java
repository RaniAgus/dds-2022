package models;

import models.exceptions.ContrasenaDebilException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Validar10MilContrasenas implements Validacion{
  private List<String> contrasenasProhibidas = new ArrayList<String>();


  @Override
  public Optional<String> validar(String Usuario,String contrasena) {
    Optional<String> error = Optional.empty();
    File listado = new File("./Weak_Paswords.txt");
    Scanner contrasenasDebiles = null;
    try {
      contrasenasDebiles = new Scanner(listado);
    } catch (FileNotFoundException e) {
      error = Optional.of("no se encontro el archivo Weak_Paswords.txt");
    }
    while (contrasenasDebiles.hasNextLine()) {
      this.contrasenasProhibidas.add(contrasenasDebiles.nextLine());
    }
    contrasenasDebiles.close();
    if (this.contrasenasProhibidas.contains(contrasena)) {
      error = Optional.of("Contraseña dentro de las 10000 mas usadas. Elija otra por favor");
    }
    return error;
  }
}

