package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
@Embeddable
public class Distancia {
  public static Distancia CERO = new Distancia(BigDecimal.ZERO, Unidad.KM);
  private BigDecimal valor;
  @Enumerated(EnumType.STRING)
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
}
