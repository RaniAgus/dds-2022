package ar.edu.utn.frba.dds;

import java.math.BigDecimal;

public class Liquidacion implements Estado {
  private BigDecimal coeficiente;

  public Liquidacion(BigDecimal coeficiente) {
    this.coeficiente = coeficiente;
  }

  @Override
  public BigDecimal modificarPrecioBase(BigDecimal precioBase) {
    return precioBase.multiply(coeficiente);
  }
}
