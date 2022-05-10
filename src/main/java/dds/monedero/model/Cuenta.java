package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
  private List<Movimiento> movimientos;
  private Long MAX_DEPOSITOS_DIARIOS = 3L;
  private Double MAX_VALOR_EXTRAIDO_DIARIO = 1000.00;

  public Cuenta(List<Movimiento> movimientos) {
    this.movimientos = new ArrayList<>(movimientos);
  }

  public void poner(double cuanto) {
    agregarDeposito(new Deposito(cuanto, LocalDate.now()));
  }

  public void sacar(double cuanto) {
    agregarExtraccion(new Extraccion(cuanto, LocalDate.now()));
  }

  public Double getSaldo() {
    return movimientos.stream()
        .mapToDouble(Movimiento::getValor)
        .sum();
  }

  private void agregarDeposito(Deposito deposito) {
    if (getDepositosA(deposito.getFecha()) >= MAX_DEPOSITOS_DIARIOS) {
      throw new MaximaCantidadDepositosException(MAX_DEPOSITOS_DIARIOS);
    }

    movimientos.add(deposito);
  }

  private Long getDepositosA(LocalDate fecha) {
    return movimientos.stream()
        .filter(movimiento -> movimiento.fueDepositadoA(fecha))
        .count();
  }

  private void agregarExtraccion(Extraccion extraccion) {
    Double limiteDeExtraccion = getLimiteDeExtraccionA(extraccion.getFecha());
    if (limiteDeExtraccion + extraccion.getValor() < 0) {
      throw new MaximoExtraccionDiarioException(
          MAX_VALOR_EXTRAIDO_DIARIO, limiteDeExtraccion
      );
    }
    Double saldo = getSaldo();
    if (saldo + extraccion.getValor() < 0) {
      throw new SaldoMenorException(saldo);
    }

    movimientos.add(extraccion);
  }

  private Double getLimiteDeExtraccionA(LocalDate fecha) {
    return MAX_VALOR_EXTRAIDO_DIARIO + movimientos.stream()
        .filter(movimiento -> movimiento.fueExtraidoA(fecha))
        .mapToDouble(Movimiento::getValor)
        .sum();
  }
}
