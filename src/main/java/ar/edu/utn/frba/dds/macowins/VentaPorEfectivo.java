package ar.edu.utn.frba.dds.macowins;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VentaPorEfectivo extends Venta {
  public VentaPorEfectivo(LocalDate fecha, List<PrendaVendida> prendasVendidas) {
    super(fecha, prendasVendidas);
  }

  @Override
  public BigDecimal aplicarRecargo() {
    return BigDecimal.ZERO;
  }
}
