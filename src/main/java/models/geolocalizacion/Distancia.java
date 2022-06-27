package models.geolocalizacion;

import java.math.BigDecimal;

public class Distancia {
  public static Distancia CERO = new Distancia(BigDecimal.ZERO, Unidad.KM);
  private BigDecimal valor;
  private Unidad unidad;

  public Distancia(BigDecimal valor, Unidad unidad) {
    this.valor = valor;
    this.unidad = unidad;
  }

  public Distancia sumar(Distancia distancia) {
    return new Distancia(valor.add(distancia.valor), Unidad.KM);
  }

  public Integer getValor() {
    return valor.intValue();
  }

  @Override
  public String toString() {
    return "Distancia{"
        + "valor=" + valor
        + ", unidad=" + unidad
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Distancia distancia = (Distancia) o;

    if (!valor.equals(distancia.valor)) {
      return false;
    }
    return unidad == distancia.unidad;
  }

  @Override
  public int hashCode() {
    int result = valor.hashCode();
    result = 31 * result + unidad.hashCode();
    return result;
  }
}
