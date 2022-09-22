package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Distancia {
  public static Distancia CERO = new Distancia(.0, Unidad.KM);
  private Double valor;
  @Enumerated(EnumType.STRING)
  private Unidad unidad;

  protected Distancia() {
  }

  public Distancia(Double valor, Unidad unidad) {
    this.valor = valor;
    this.unidad = unidad;
  }

  public Distancia sumar(Distancia distancia) {
    return new Distancia(valor + distancia.getValor(), Unidad.KM);
  }

  public Double getValor() {
    return valor;
  }

  @Override
  public String toString() {
    return "Distancia{"
        + "valor=" + valor
        + ", unidad=" + unidad
        + '}';
  }
}
