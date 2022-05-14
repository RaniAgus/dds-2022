package models.factory;

import models.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ValidadorFactory {
  public static Validador validador() throws FileNotFoundException {
    ArrayList<Validacion> listaValidaciones=new ArrayList<Validacion>();
   listaValidaciones.add(new Validar8Caracteres());
   listaValidaciones.add(new Validar10MilContrasenas());
   listaValidaciones.add(new ValidarCaracteresConsecutivos());
   listaValidaciones.add(new ValidarCaracteresRepetidos());
   listaValidaciones.add(new ValidarUsuarioPorDefecto());
   return new Validador(listaValidaciones);
  }
}
