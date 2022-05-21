package models.miembro;

import models.Ubicacion;
import models.api.Distancia;
import models.api.GeoddsApi;
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
  public Distancia getDistanciaDelTramo(GeoddsApiImplementation apiDistancia){
    return apiDistancia.medirDistancia(ubicacionInicial,ubicacionFinal);
  }
}
