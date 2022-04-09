package ar.edu.utn.frba.dds;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrendaTest {
  @Test
  public void dadaUnaPrendaSePuedeSaberSuTipo() {
    Prenda prenda = camisaConPrecioBase100(new Nueva());

    assertEquals(Tipo.CAMISA, prenda.getTipo());
  }

  @Test
  public void dadaUnaPrendaNuevaSuPrecioDeVentaEquivaleAlPrecioBase() {
    Prenda prenda = camisaConPrecioBase100(new Nueva());

    assertThat(prenda.getPrecio(), comparesEqualTo(valueOf(100)));
  }

  @Test
  public void dadaUnaPrendaEnPromocionSuPrecioDeVentaRestaUnValorFijoAlPrecioBase() {
    Prenda prenda = camisaConPrecioBase100(new Promocion(valueOf(25)));

    assertThat(prenda.getPrecio(), comparesEqualTo(valueOf(75)));
  }

  @Test
  public void dadaUnaPrendaEnLiquidacionSuPrecioDeVentaEsLaMitadDeSuPrecioBase() {
    Prenda prenda = camisaConPrecioBase100(new Liquidacion(valueOf(0.5)));

    assertThat(prenda.getPrecio(), comparesEqualTo(valueOf(50)));
  }

  private Prenda camisaConPrecioBase100(Estado estado) {
    return new Prenda(valueOf(100), Tipo.CAMISA, estado);
  }
}
