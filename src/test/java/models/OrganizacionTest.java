package models;

import models.da.DatosActividadesParser;
import models.organizacion.Organizacion;
import models.organizacion.Sector;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class OrganizacionTest extends BaseTest {

  // [TPA1]: Se debe permitir el alta de Organizaciones y de sectores dentro de cada una de estas.

  @Test
  public void sePuedeDarDeAltaUnSector() {
    Organizacion utn = crearOrganizacionVacia();
    Sector unSectorVacio = crearSectorVacio();

    utn.darDeAltaSector(unSectorVacio);

    assertThat(utn.getSectores()).containsExactly(unSectorVacio);
  }

  // [TPA2]: Se debe permitir la carga de mediciones por parte de una Organización, respetando la estructura del archivo
  // mencionada.

  @Test
  public void sePuedeCargarMedicionesDeUnaOrganizacion() {
    Organizacion organizacion = crearOrganizacionVacia();
    when(lectorDeArchivos.getLineas()).thenReturn(Arrays.asList(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "ELECTRICIDAD;122;MENSUAL;03/2002",
        "NAFTA;5;ANUAL;2020"
    ));
    DatosActividadesParser parser = crearParserDatosDeActividad();

    organizacion.agregarDatosActividad(parser.getDatosActividad());

    assertThat(organizacion.getDatosActividad()).hasSize(2);
  }
}
