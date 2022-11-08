package ar.edu.utn.frba.dds.impactoambiental.models.da;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LectorDeArchivos implements Lector {
  private String path;

  public LectorDeArchivos(String path) {
    this.path = path;
  }

  @Override
  public List<String> getLineas() {
    File archivo = new File(path);
    validarArchivo(archivo);
    return archivoALineas(archivo);
  }

  private void validarArchivo(File f) {
    if (!f.exists()) {
      throw new IllegalArgumentException("El archivo no existe.");
    }
    if (!f.canRead()) {
      throw new IllegalArgumentException("No se puede leer el archivo.");
    }
    if (f.isDirectory()) {
      throw new IllegalArgumentException("La ruta especifiacada corresponde a un directorio.");
    }
  }

  private List<String> archivoALineas(File archivo) {
    List<String> nuevasLineas = new ArrayList<>();
    try {
      Scanner scannerArchivo = new Scanner(archivo);
      while (scannerArchivo.hasNextLine()) {
        nuevasLineas.add(scannerArchivo.nextLine());
      }
      scannerArchivo.close();
      return nuevasLineas;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("El archivo no existe.");
    }
  }
}
