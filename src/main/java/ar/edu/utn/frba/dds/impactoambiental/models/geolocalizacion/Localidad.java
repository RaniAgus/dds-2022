package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

public class Localidad {
  private final Integer id;
  private final String nombre;
  private final String codPostal;
  private final Municipio municipio;

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
    return "Localidad{"
        + "id=" + id
        + ", nombre='" + nombre + '\''
        + ", codPostal='" + codPostal + '\''
        + ", municipio=" + municipio
        + '}';
  }
}
