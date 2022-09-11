package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.LectorDeArchivos;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LectorDeArchivosTest extends BaseTest {
  @Test
  public void sePuedeLeerUnArchivo() {
    LectorDeArchivos lector = new LectorDeArchivos("./src/test/resources/DA_correcto.csv");

    assertThat(lector.getLineas()).containsExactly(
        "TIPO_CONSUMO;VALOR;PERIODICIDAD;PERIODO",
        "ELECTRICIDAD;122;MENSUAL;03/2002",
        "NAFTA;5;ANUAL;2020"
    );
  }
}
