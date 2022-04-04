package ar.edu.utn.frba.dds;

import java.math.BigDecimal;

public class Nueva implements Estado {
  @Override
  public BigDecimal modificarPrecioBase(BigDecimal precioBase) {
    return precioBase;
  }
}
