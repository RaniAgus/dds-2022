package ar.edu.utn.frba.dds;

import java.math.BigDecimal;

public class Promocion implements Estado {
  private BigDecimal descuento;

  public Promocion(BigDecimal descuento) {
    this.descuento = descuento;
  }

  @Override
  public BigDecimal modificarPrecioBase(BigDecimal precioBase) {
    return precioBase.subtract(descuento);
  }
}
