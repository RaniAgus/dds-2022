package models.geolocalizacion;

public class Pais {
  private Integer id;
  private String nombre;

  public Pais(Integer id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Integer getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return "Pais{"
        + "id=" + id
        + ", nombre='" + nombre + '\''
        + '}';
  }
}
