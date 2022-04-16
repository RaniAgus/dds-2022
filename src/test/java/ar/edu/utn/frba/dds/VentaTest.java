package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class VentaTest {
  @Test
  public void dadaUnaVentaPorEfectivoNoSeModificaSuPrecio() {
    Venta venta = new VentaPorEfectivo(
        LocalDate.now(),
        asList(
            prendaVendida(100, 2),
            prendaVendida(200, 3)
        )
    );

    assertThat(venta.getPrecioTotal(), comparesEqualTo(valueOf(800)));
  }

  @Test
  public void dadaUnaVentaPorEfectivoSeModificaSuPrecioSegunCantidadDeCuotasYPrendas() {
    Venta venta = new VentaConTarjeta(
        LocalDate.now(),
        asList(
            prendaVendida(100, 2),
            prendaVendida(200, 3)
        ),
        12, valueOf(10), valueOf(0.1)
    );

    assertThat(venta.getPrecioTotal(), comparesEqualTo(valueOf(800 + 120 + 80)));
  }

  private PrendaVendida prendaVendida(Integer precio, Integer cantidad) {
    Prenda prenda = new Prenda(valueOf(precio), Tipo.CAMISA, new Nueva());
    return new PrendaVendida(prenda, prenda.getPrecio(), cantidad);
  }
}
