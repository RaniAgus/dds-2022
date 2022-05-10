package dds.monedero.model;

import java.time.LocalDate;

public interface Movimiento {
  Double getValor();
  boolean fueDepositadoA(LocalDate fecha);
  boolean fueExtraidoA(LocalDate fecha);
}
