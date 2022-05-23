package models.miembro;

import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.mediodetransporte.MedioDeTransporte;


public class Tramo {
  private Ubicacion ubicacionInicial;
  private Ubicacion ubicacionFinal;
  private MedioDeTransporte medioDeTransporte;

  public Tramo(Ubicacion ubicacionInicial,
               Ubicacion ubicacionFinal,
               MedioDeTransporte medioDeTransporte) {
    this.ubicacionInicial = ubicacionInicial;
    this.ubicacionFinal = ubicacionFinal;
    this.medioDeTransporte = medioDeTransporte;
  }
  public Distancia getDistanciaDelTramo(Geolocalizador apiDistancia){
    return apiDistancia.medirDistancia(ubicacionInicial,ubicacionFinal);
  }
}
