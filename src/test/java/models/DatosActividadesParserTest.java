package models;


import models.da.DatoActividad;
import models.da.DatosActividadesParser;
import models.da.Periodicidad;
import models.da.TipoDeConsumo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class DatosActividadesParserTest extends BaseTest {
  @Test
  public void sePuedeCargarDatosDeActividadConPeriodicidadMensual() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lectorDeArchivos.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "ELECTRICIDAD;122;MENSUAL;03/2002"
    ));

    List<DatoActividad> datosActividad = parser.getDatosActividad();

    assertThat(datosActividad.get(0))
        .extracting("valor", "periodicidad", "periodo", "tipo")
        .containsExactly(122.0, Periodicidad.MENSUAL, "03/2002", TipoDeConsumo.ELECTRICIDAD);
  }

  @Test
  public void sePuedeCargarDatosDeActividadConPeriodicidadAnual() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lectorDeArchivos.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "NAFTA;5;ANUAL;2020"
    ));

    List<DatoActividad> datosActividad = parser.getDatosActividad();

    assertThat(datosActividad.get(0))
        .extracting("valor", "periodicidad", "periodo", "tipo")
        .containsExactly(5.0, Periodicidad.ANUAL, "2020", TipoDeConsumo.NAFTA);
  }

  @Test
  public void noSePuedenCargarDAsConCamposFaltantes() {
    DatosActividadesParser parser = crearParserDatosDeActividad();
    when(lectorDeArchivos.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "122;MENSUAL;03/2002",
        "NAFTA;5;2020"
    ));

    assertThatThrownBy(parser::getDatosActividad)
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessage("La linea no tiene el numero adecuado de campos.");
  }
}
