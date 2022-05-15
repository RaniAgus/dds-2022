package models.miembro;

import java.util.ArrayList;
import java.util.List;

public class Trayecto {
  private List<Tramo> tramos;

  public Trayecto(List<Tramo> tramos) {
    this.tramos = new ArrayList<>(tramos);
  }
}
