package ar.edu.utn.frba.dds;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Venta {
  private LocalDate dia;
  private List<PrendaVendida> prendasVendidas;

  public Venta(LocalDate dia, List<PrendaVendida> prendasVendidas) {
    this.dia = dia;
    this.prendasVendidas = new ArrayList<>(prendasVendidas);
  }

  public BigDecimal getPrecioTotal() {
    return sumaPreciosTotales().add(aplicarRecargo());
  }

  public Boolean esDelDia(LocalDate dia) {
    return dia.isEqual(this.dia);
  }

  public abstract BigDecimal aplicarRecargo();

  protected BigDecimal sumaPreciosTotales() {
    return prendasVendidas.stream()
        .map(PrendaVendida::getPrecioTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
