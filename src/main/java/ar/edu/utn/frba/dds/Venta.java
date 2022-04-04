package ar.edu.utn.frba.dds;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Venta {
  private LocalDate fecha;
  private List<PrendaVendida> prendasVendidas;

  public Venta(LocalDate fecha, List<PrendaVendida> prendasVendidas) {
    this.fecha = fecha;
    this.prendasVendidas = new ArrayList<>(prendasVendidas);
  }

  public BigDecimal getPrecioTotal() {
    return sumaPreciosTotales().add(aplicarRecargo());
  }

  public Boolean esDeFecha(LocalDate otraFecha) {
    return fecha.isEqual(otraFecha);
  }

  public abstract BigDecimal aplicarRecargo();

  protected BigDecimal sumaPreciosTotales() {
    return prendasVendidas.stream()
        .map(PrendaVendida::getPrecioTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
