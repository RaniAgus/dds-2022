package models.geolocalizacion;

public class Ubicacion {
  private Integer idLocalidad;
  private String calle;
  private String altura;

  public Ubicacion(Integer idLocalidad, String calle, String altura) {
    this.idLocalidad = idLocalidad;
    this.calle = calle;
    this.altura = altura;
  }

  public Integer getIdLocalidad() {
    return idLocalidad;
  }

  public String getCalle() {
    return calle;
  }

  public String getAltura() {
    return altura;
  }

  @Override
  public String toString() {
    return "Ubicacion{" +
        "idLocalidad=" + idLocalidad +
        ", calle='" + calle + '\'' +
        ", altura='" + altura + '\'' +
        '}';
  }
}
