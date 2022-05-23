package models.geolocalizacion;

public class Localidad {
  private Integer id;
  private String nombre;
  private String codPostal;
  private Municipio municipio;

  public Localidad(Integer id, String nombre, String codPostal, Municipio municipio) {
    this.id = id;
    this.nombre = nombre;
    this.codPostal = codPostal;
    this.municipio = municipio;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Localidad{" +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        ", codPostal='" + codPostal + '\'' +
        ", municipio=" + municipio +
        '}';
  }
}
