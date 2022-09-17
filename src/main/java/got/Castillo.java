package got;

import got.Lugar;

public class Castillo extends Lugar {
  private int cantidadTorres;
  private int cantidadMurallas;

  public Castillo(int cantidadTorres, int cantidadMurallas) {
    this.cantidadTorres = cantidadTorres;
    this.cantidadMurallas = cantidadMurallas;
  }
}
