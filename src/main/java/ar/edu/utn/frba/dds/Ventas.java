package ar.edu.utn.frba.dds;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ventas {
  List<Venta> ventas;

  public Ventas(List<Venta> ventas) {
    this.ventas = new ArrayList<>(ventas);
  }

  public BigDecimal gananciasDelDia(LocalDate dia) {
    return ventas.stream()
        .filter(venta -> venta.esDelDia(dia))
        .map(Venta::getPrecioTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
