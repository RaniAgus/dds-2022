package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
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

  @Test
  public void dadaUnaColeccionDeVentasSePuedenObtenerLasGananciasDeUnDia() {
    List<Venta> ventas = asList(
        venta(LocalDate.now(), 100),
        venta(LocalDate.now(), 500),
        venta(LocalDate.now().minusDays(1), 1000),
        venta(LocalDate.now().minusDays(1), 5000)
    );

    BigDecimal gananciasDeHoy = ventas.stream()
        .filter(venta -> venta.esDeFecha(LocalDate.now()))
        .map(Venta::getPrecioTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    assertThat(gananciasDeHoy, comparesEqualTo(valueOf(600)));
  }

  private PrendaVendida prendaVendida(Integer precio, Integer cantidad) {
    Prenda prenda = new Prenda(valueOf(precio), Tipo.CAMISA, new Nueva());
    return new PrendaVendida(prenda, prenda.getPrecio(), cantidad);
  }

  private Venta venta(LocalDate fecha, Integer precio) {
    return new VentaPorEfectivo(fecha, singletonList(prendaVendida(precio, 1)));
  }
}
