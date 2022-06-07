package models.da;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVLoader {

  List<String> lineas;

  public CSVLoader(String path) throws FileNotFoundException {

    File archivo = new File(path);
    validarArchivo(archivo);

    this.lineas = archivoALineas(archivo);
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

  private List<String> archivoALineas(File archivo) throws FileNotFoundException {
    List<String> lineas = new ArrayList<>();
    Scanner scannerArchivo = new Scanner(archivo);

    while(scannerArchivo.hasNextLine()) {
      lineas.add(scannerArchivo.nextLine());
    }

    return lineas;
  }

  public List<String> getLineas() {
    return lineas;
  }
}
