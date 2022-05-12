package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
  private List<Movimiento> movimientos;
  private static Long MAX_DEPOSITOS_DIARIOS = 3L;
  private static Double MAX_VALOR_EXTRAIDO_DIARIO = 1000.00;

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
    validarMaximoDepositosDiarios(deposito);
    movimientos.add(deposito);
  }

  private void agregarExtraccion(Extraccion extraccion) {
    validarLimiteDeExtraccionDiario(extraccion);
    validarSaldoPositivo(extraccion);
    movimientos.add(extraccion);
  }

  private void validarMaximoDepositosDiarios(Deposito deposito) {
    long depositos = movimientos.stream()
        .filter(movimiento -> movimiento.fueDepositadoA(deposito.getFecha()))
        .count();

    if (depositos >= MAX_DEPOSITOS_DIARIOS) {
      throw new MaximaCantidadDepositosException(MAX_DEPOSITOS_DIARIOS);
    }
  }

  private void validarLimiteDeExtraccionDiario(Extraccion extraccion) {
    double limiteDeExtraccion = MAX_VALOR_EXTRAIDO_DIARIO + movimientos.stream()
        .filter(movimiento -> movimiento.fueExtraidoA(extraccion.getFecha()))
        .mapToDouble(Movimiento::getValor)
        .sum();

    if (limiteDeExtraccion + extraccion.getValor() < 0) {
      throw new MaximoExtraccionDiarioException(
          MAX_VALOR_EXTRAIDO_DIARIO, limiteDeExtraccion
      );
    }
  }

  private void validarSaldoPositivo(Extraccion extraccion) {
    Double saldo = getSaldo();
    if (saldo + extraccion.getValor() < 0) {
      throw new SaldoMenorException(saldo);
    }
  }
}
