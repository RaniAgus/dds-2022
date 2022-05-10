package dds.monedero.exceptions;

import java.math.BigDecimal;

public class MontoNegativoException extends RuntimeException {
  public MontoNegativoException(Double monto) {
    super(monto + ": el monto a ingresar debe ser un valor positivo");
  }
}