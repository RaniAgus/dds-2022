package dds.monedero.model;

import dds.monedero.exceptions.MontoNegativoException;

import java.time.LocalDate;

public class Deposito implements Movimiento {
  private Double monto;
  private LocalDate fecha;

  public Deposito(Double monto, LocalDate fecha) {
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
    return this.monto;
  }

  @Override
  public boolean fueDepositadoA(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  @Override
  public boolean fueExtraidoA(LocalDate fecha) {
    return false;
  }
}