package models;


import models.da.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class cargaDATest {
  @Test
  public void sePuedeCargarDAValidos() throws IOException {

    CSVLoader csvLoader = new CSVLoader("./src/test/resources/DA_correcto.csv");
    DatosActividadesParser DAParser = new DatosActividadesParser(csvLoader).setSkiplines(1);
    List<DatoActividad> DAs = DAParser.getDatosActividad();

    DatoActividad DA1 = DAs.get(0);
    DatoActividad DA2 = DAs.get(1);

    Assertions.assertEquals(122, DA1.getValor());
    Assertions.assertEquals(Periodicidad.MENSUAL, DA1.getPeriodicidad());
    Assertions.assertEquals("03/2002", DA1.getPeriodo());
    Assertions.assertEquals(TipoDeConsumo.ELECTRICIDAD, DA1.getTipo());

    Assertions.assertEquals(5, DA2.getValor());
    Assertions.assertEquals(Periodicidad.ANUAL, DA2.getPeriodicidad());
    Assertions.assertEquals("2020", DA2.getPeriodo());
    Assertions.assertEquals(TipoDeConsumo.NAFTA, DA2.getTipo());
  }

  @Test
  public void noSePuedenCargarDAsConCamposFaltantes() throws FileNotFoundException {
    CSVLoader csvLoader = new CSVLoader("./src/test/resources/DA_campos_faltantes.csv");
    DatosActividadesParser DAParser = new DatosActividadesParser(csvLoader).setSkiplines(1);
    Assertions.assertThrows(IllegalArgumentException.class, DAParser::getDatosActividad);
  }

}
