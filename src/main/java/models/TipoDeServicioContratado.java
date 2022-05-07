package models;

import static java.util.Objects.requireNonNull;

public class TipoDeServicioContratado {
  private String nombre;

  public TipoDeServicioContratado(String nombre) {
    this.nombre = requireNonNull(nombre, "Un servicio contratado debe llevar nombre");
  }

  public static final TipoDeServicioContratado TAXI = new TipoDeServicioContratado("Taxi");
  public static final TipoDeServicioContratado REMIS = new TipoDeServicioContratado("Remis");
}
