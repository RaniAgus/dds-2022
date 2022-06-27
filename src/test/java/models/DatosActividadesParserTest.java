package models;


import models.da.DatoActividad;
import models.da.DatosActividadesParser;
import models.da.Periodicidad;
import models.da.TipoDeConsumo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.when;

public class DatosActividadesParserTest extends BaseTest {
  @Test
  public void sePuedeCargarDatosDeActividadConPeriodicidadMensual() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lectorDeArchivos.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "ELECTRICIDAD;122;MENSUAL;03/2002"
    ));

    DatoActividad datoActividad = parser.getDatosActividad().get(0);

    Assertions.assertEquals(122, datoActividad.getValor());
    Assertions.assertEquals(Periodicidad.MENSUAL, datoActividad.getPeriodicidad());
    Assertions.assertEquals("03/2002", datoActividad.getPeriodo());
    Assertions.assertEquals(TipoDeConsumo.ELECTRICIDAD, datoActividad.getTipo());
  }

  @Test
  public void sePuedeCargarDatosDeActividadConPeriodicidadAnual() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lectorDeArchivos.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "NAFTA;5;ANUAL;2020"
    ));

    DatoActividad datoActividad = parser.getDatosActividad().get(0);

    Assertions.assertEquals(5, datoActividad.getValor());
    Assertions.assertEquals(Periodicidad.ANUAL, datoActividad.getPeriodicidad());
    Assertions.assertEquals("2020", datoActividad.getPeriodo());
    Assertions.assertEquals(TipoDeConsumo.NAFTA, datoActividad.getTipo());
  }

  @Test
  public void noSePuedenCargarDAsConCamposFaltantes() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lectorDeArchivos.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "122;MENSUAL;03/2002",
        "NAFTA;5;2020"
    ));

    Assertions.assertThrows(IllegalArgumentException.class, parser::getDatosActividad);
  }

}
