package models.api;

public class Distancia {
  private String valor;
  private String unidad;

  public Distancia(String valor, String unidad) {
    this.valor = valor;
    this.unidad = unidad;
  }

  public String getValor() {
    return valor;
  }

  public String getUnidad() {
    return unidad;
  }

  @Override
  public String toString() {
    return "Distancia{" +
        "valor='" + valor + '\'' +
        ", unidad='" + unidad + '\'' +
        '}';
  }
}
