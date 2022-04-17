package ar.edu.utn.frba.dds.macowins;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.*;

public class PrendaTest {

  @Test
  public void elTipoDeUnaCamisaNuevaEsCAMISA() {
    assertEquals(camisaNueva(200).getTipo().toString(), "CAMISA");
  }

  @Test
  public void elTipoDeUnSacoEnLiquidacionEsSACO() {
    assertEquals(sacoEnLiquidacion(200).getTipo().toString(), "SACO");
  }

  @Test
  public void elPrecioDeUnaCamisaNuevaEsSuPrecioBase() {
    assertThat(camisaNueva(4000).getPrecio(), comparesEqualTo(valueOf(4000)));
    assertThat(camisaNueva(5000).getPrecio(), comparesEqualTo(valueOf(5000)));
  }

  @Test
  public void elPrecioDeUnSacoEnLiquidacionEsSuLaMitadDeSuPrecioBase() {
    assertThat(sacoEnLiquidacion(3000).getPrecio(), comparesEqualTo(valueOf(1500)));
    assertThat(sacoEnLiquidacion(8000).getPrecio(), comparesEqualTo(valueOf(4000)));
  }

  @Test
  public void elPrecioDeUnPantalonEnPromocionEsSuPrecioBaseMenosSuDescuento() {
    assertThat(pantalonEnPromocion(1500, 200).getPrecio(), comparesEqualTo(valueOf(1300)));
    assertThat(pantalonEnPromocion(1500, 100).getPrecio(), comparesEqualTo(valueOf(1400)));
  }

  private Prenda pantalonEnPromocion(int precioBase, int descuento) {
    return new Prenda(valueOf(precioBase), Tipo.PANTALON, new Promocion(valueOf(descuento)));
  }

  private Prenda camisaNueva(double precioBase) {
    return new Prenda(valueOf(precioBase), Tipo.CAMISA, new Nueva());
  }

  private Prenda sacoEnLiquidacion(double precioBase) {
    return new Prenda(valueOf(precioBase), Tipo.SACO, new Liquidacion(valueOf(0.5)));
  }
}
