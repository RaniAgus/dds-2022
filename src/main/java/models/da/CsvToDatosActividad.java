package models.da;

import com.opencsv.bean.CsvToBeanBuilder;

import java.util.List;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class CsvToDatosActividad {

  private CsvToDatosActividad() {}

  public static List<DatoActividad> leerDeArchivo(String path) throws FileNotFoundException{
    return new CsvToBeanBuilder<DatoActividad>(new FileReader(path)).withType(DatoActividad.class).withSeparator(';').withSkipLines(1).build().parse();
  }
}
