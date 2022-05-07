package models;

public class TipoDeServicioContratado {
  private String nombre;

  public TipoDeServicioContratado(String nombre) {
    this.nombre = nombre;
  }

  public static final TipoDeServicioContratado TAXI = new TipoDeServicioContratado("Taxi");
  public static final TipoDeServicioContratado REMIS = new TipoDeServicioContratado("Remis");
}
