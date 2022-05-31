package models.geolocalizacion;

public class Provincia {
  private Integer id;
  private String nombre;
  private Pais pais;

  public Provincia(Integer id, String nombre, Pais pais) {
    this.id = id;
    this.nombre = nombre;
    this.pais = pais;
  }

  public Integer getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return "Provincia{" +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        ", pais=" + pais +
        '}';
  }
}
