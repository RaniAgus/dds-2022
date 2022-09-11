package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

public class Municipio {
  private Integer id;
  private String nombre;
  private Provincia provincia;

  public Municipio(Integer id, String nombre, Provincia provincia) {
    this.id = id;
    this.nombre = nombre;
    this.provincia = provincia;
  }

  public Integer getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return "Municipio{"
        + "id=" + id
        + ", nombre='" + nombre + '\''
        + ", provincia=" + provincia
        + '}';
  }
}
