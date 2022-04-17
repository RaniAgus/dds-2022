package ar.edu.utn.frba.dds.macowins;

import java.math.BigDecimal;

public class Prenda {
  private BigDecimal precioBase;
  private Tipo tipo;
  private Estado estado;

  public Prenda(BigDecimal precioBase, Tipo tipo, Estado estado) {
    this.precioBase = precioBase;
    this.tipo = tipo;
    this.estado = estado;
  }

  public Tipo getTipo() {
    return tipo;
  }

  public BigDecimal getPrecio() {
    return estado.modificarPrecioBase(precioBase);
  }
}
