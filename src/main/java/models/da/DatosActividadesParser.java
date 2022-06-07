package models.da;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatosActividadesParser {
  Character separator;
  Integer skiplines;
  CSVLoader csvLoader;

  public DatosActividadesParser(CSVLoader loader) {
    this.csvLoader = loader;
    this.skiplines = 0;
    this.separator = ';';
  }

  public DatosActividadesParser setSeparator(Character separator) {
    this.separator = separator;
    return this;
  }

  public DatosActividadesParser setSkiplines(Integer skiplines) {
    this.skiplines = skiplines;
    return this;
  }

  private DatoActividad parseLine(String line) {
    List<String> campos = Arrays.asList(line.split(separator.toString()));
    if (campos.size() != 4) {
      throw new IllegalArgumentException("La linea no tiene el numero adecuado de campos.");
    }
    TipoDeConsumo tipoDeConsumo = TipoDeConsumo.valueOf(campos.get(0));
    Double valor = Double.parseDouble(campos.get(1));
    Periodicidad periodicidad = Periodicidad.valueOf(campos.get(2));
    String periodo = campos.get(3);

    return new DatoActividad(tipoDeConsumo, valor, periodicidad, periodo);
  }

  public List<DatoActividad> getDatosActividad() {
    List<String> lineas = csvLoader.getLineas();
    lineas = lineas.subList(skiplines, lineas.size());

    return lineas.stream().map(this::parseLine).collect(Collectors.toList());
  }
}
