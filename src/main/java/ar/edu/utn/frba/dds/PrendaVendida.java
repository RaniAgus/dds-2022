package ar.edu.utn.frba.dds;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public class PrendaVendida {
  private Prenda prenda;
  private BigDecimal precio;
  private Integer cantidad;

  public PrendaVendida(Prenda prenda, BigDecimal precio, Integer cantidad) {
    this.prenda = prenda;
    this.precio = precio;
    this.cantidad = cantidad;
  }

  public BigDecimal getPrecioTotal() {
    return precio.multiply(valueOf(cantidad));
  }
}
