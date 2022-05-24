package models.miembro;

import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.mediodetransporte.MedioDeTransporte;


public interface Tramo {
  Ubicacion ubicacionInicial = null;
  Ubicacion ubicacionFinal = null;
  MedioDeTransporte medioDeTransporte = null;

  public boolean esCompartible();

}
