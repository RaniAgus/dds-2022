package dds.monedero.model;

import dds.monedero.exceptions.MontoNegativoException;

import java.time.LocalDate;

public class Extraccion implements Movimiento {
  private Double monto;
  private LocalDate fecha;

  public Extraccion(Double monto, LocalDate fecha) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto);
    }
    this.monto = monto;
    this.fecha = fecha;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  @Override
  public Double getValor() {
    return this.monto * -1.00;
  }

  @Override
  public boolean fueDepositadoA(LocalDate fecha) {
    return false;
  }

  @Override
  public boolean fueExtraidoA(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

}
