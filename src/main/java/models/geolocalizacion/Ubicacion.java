package models.geolocalizacion;

/******************************************************************************
 *  Source:       https://introcs.cs.princeton.edu/java/44st/Location.java.html
 *  Immutable data type for a named location: name and (latitude, longitude).
 ******************************************************************************/

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
}
