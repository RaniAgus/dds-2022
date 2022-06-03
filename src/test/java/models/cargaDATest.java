package models;

import models.da.CsvToDatosActividad;
import models.da.DatoActividad;
import models.da.Periodicidad;
import models.da.TipoDeConsumo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class cargaDATest {
  @Test
  public void sePuedeCargarDAValidos() throws IOException {
    List<DatoActividad> DAs = CsvToDatosActividad.leerDeArchivo("./src/test/java/models/dummy_DA.csv");
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

}
