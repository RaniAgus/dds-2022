package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class VentasTest {
  @Test
  public void dadaUnaColeccionDeVentasAlCalcularLasGananciasDeUnDiaSeSumanSoloLasVentasDeEseDia() {
    Ventas ventas = new Ventas(asList(
        venta(now(), 100),
        venta(now(), 500),
        venta(now().minusDays(1), 1000),
        venta(now().minusDays(1), 5000)
    ));

    assertThat(ventas.gananciasDelDia(now()), comparesEqualTo(valueOf(600)));
  }

  private PrendaVendida prendaVendida(Integer precio) {
    Prenda prenda = new Prenda(valueOf(precio), Tipo.CAMISA, new Nueva());
    return new PrendaVendida(prenda, prenda.getPrecio(), 1);
  }

  private Venta venta(LocalDate fecha, Integer precio) {
    return new VentaPorEfectivo(fecha, singletonList(prendaVendida(precio)));
  }
}
