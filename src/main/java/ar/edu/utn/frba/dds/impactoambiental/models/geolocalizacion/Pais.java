package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

public class Pais {
  private final Integer id;
  private final String nombre;

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
