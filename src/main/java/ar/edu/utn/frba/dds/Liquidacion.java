package ar.edu.utn.frba.dds;

import java.math.BigDecimal;

public class Liquidacion implements Estado {
  private BigDecimal multiplicador;

  public Liquidacion(BigDecimal multiplicador) {
    this.multiplicador = multiplicador;
  }

  @Override
  public BigDecimal modificarPrecioBase(BigDecimal precioBase) {
    return precioBase.multiply(multiplicador);
  }
}
