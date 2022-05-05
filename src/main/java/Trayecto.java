import java.util.List;

public class Trayecto {
  private List<Tramo> tramos;

  public Trayecto(List<Tramo> tramos) {
    if (tramos.isEmpty()) {
      throw new TrayectoException("El trayecto debe contener al menos un tramo");
    }
    this.tramos = tramos;
  }
}
