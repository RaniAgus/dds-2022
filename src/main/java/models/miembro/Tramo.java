package models.miembro;

import models.geolocalizacion.Distancia;

public interface Tramo {

  Distancia getDistancia();

  boolean esCompartible();

  Double carbonoEquivalente();
}
