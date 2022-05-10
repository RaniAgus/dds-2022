package dds.monedero.exceptions;

public class MaximoExtraccionDiarioException extends RuntimeException {
  public MaximoExtraccionDiarioException(double maximoValorExtraidoDiario, double limite) {
    super("No puede extraer mas de $ " + maximoValorExtraidoDiario + " diarios, límite: " + limite);
  }
}