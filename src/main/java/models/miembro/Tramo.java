package models.miembro;

import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.mediodetransporte.MedioDeTransporte;


public interface Tramo {

  public Distancia getDistancia();

  public boolean esCompartible();

}
