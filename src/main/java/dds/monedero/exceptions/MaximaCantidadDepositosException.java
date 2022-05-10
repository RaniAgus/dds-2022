package dds.monedero.exceptions;

public class MaximaCantidadDepositosException extends RuntimeException {

  public MaximaCantidadDepositosException(Long maximosDepositosDiarios) {
    super("Ya excedio los " + maximosDepositosDiarios + " depositos diarios");
  }

}