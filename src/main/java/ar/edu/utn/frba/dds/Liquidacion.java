package ar.edu.utn.frba.dds;

import java.math.BigDecimal;

public class Liquidacion implements Estado {
  private BigDecimal porcentaje;

  public Liquidacion(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }

  @Override
  public BigDecimal modificarPrecioBase(BigDecimal precioBase) {
    return precioBase.multiply(BigDecimal.ONE.subtract(porcentaje));
  }
}
