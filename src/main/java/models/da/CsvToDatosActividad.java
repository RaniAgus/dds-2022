package models.da;

import com.opencsv.bean.CsvToBeanBuilder;

import java.util.List;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class CsvToDatosActividad {
  public List<DatoActividad> leerDeArchivo(String path) throws FileNotFoundException{
    return new CsvToBeanBuilder<DatoActividad>(new FileReader(path)).withType(DatoActividad.class).withSeparator(';').withSkipLines(1).build().parse();
  }
}
