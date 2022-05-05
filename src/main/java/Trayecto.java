import java.util.List;

public class Trayecto {
  private List<Tramo> tramos;

  public Trayecto(List<Tramo> tramos) {
    this.tramos = tramos;
  }

  public void agregarTramo(Tramo nuevoTramo) {
    this.tramos.add(nuevoTramo);
  }

}
