package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

public class GeoddsError {
  private Integer code;
  private String message;

  public String getErrorMessage() {
    return String.format("Error %d: %s", code, message);
  }
}
