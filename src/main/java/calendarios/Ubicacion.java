package calendarios;

public class Ubicacion {
  private double latitud;
  private double longitud;

  public Ubicacion(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public double getLatitud() {
    return latitud;
  }

  public double getLongitud() {
    return longitud;
  }

  @Override
  public String toString() {
    return "Ubicacion{" +
        "latitud=" + latitud +
        ", longitud=" + longitud +
        '}';
  }
}
