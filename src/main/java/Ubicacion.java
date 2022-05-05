/******************************************************************************
 *  Source:       https://introcs.cs.princeton.edu/java/44st/Location.java.html
 *  Immutable data type for a named location: name and (latitude, longitude).
 ******************************************************************************/

public class Ubicacion {
  private String nombre;
  private Double longitud;
  private Double latitud;

  /**
   * @param nombre: nombre de la ubicación
   * @param latitud: latitud en grados
   * @param longitud: longitud en grados
   */
  public Ubicacion(String nombre, Double latitud, Double longitud) {
    this.nombre = nombre;
    this.latitud = latitud;
    this.longitud = longitud;
  }

  /**
   * @param that: la otra Ubicacion
   * @return devuelve la distancia en kilómetros entre una Ubicacion y otra
   */
  public Double distanciaA(Ubicacion that) {
    // conversión de latitudes y longitudes a radianes
    double lat1 = Math.toRadians(this.latitud);
    double lon1 = Math.toRadians(this.longitud);
    double lat2 = Math.toRadians(that.latitud);
    double lon2 = Math.toRadians(that.longitud);

    // ángulo en radianes, usando la fórmula del teorema del coseno
    double angulo = Math.acos(Math.sin(lat1) * Math.sin(lat2)
        + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

    // cada grado de inclinación de la Tierra equivale a 111.12km aprox
    double KILOMETROS_POR_GRADO = 111.12;

    // producto entre el ángulo en grados y los kilómetros por grado
    return Math.toDegrees(angulo) * KILOMETROS_POR_GRADO;
  }

  @Override
  public String toString() {
    return nombre + " (" + latitud + ", " + longitud + ")";
  }
}
