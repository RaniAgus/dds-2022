package models.miembro;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.geolocalizacion.Ubicacion;
import models.mediodetransporte.MedioDeTransporte;

public class TramoPrivado implements Tramo {

  Ubicacion ubicacionInicial;
  Ubicacion ubicacionFinal;
  Distancia distancia;
  MedioDeTransporte medioDeTransporte;
  Geolocalizador calculadorDistancia;

  public TramoPrivado(Geolocalizador calculadorDistancia,
                      Ubicacion ubicacionInicial,
                      Ubicacion ubicacionFinal,
                      MedioDeTransporte medioDeTransporte) {
    this.calculadorDistancia = calculadorDistancia;
    this.ubicacionFinal = ubicacionFinal;
    this.ubicacionInicial = ubicacionInicial;
    this.distancia = calculadorDistancia.medirDistancia(ubicacionInicial,ubicacionFinal);
    this.medioDeTransporte = medioDeTransporte;


  }

  @Override
  public Distancia getDistancia() {
    return distancia;
  }

  @Override
  public boolean esCompartible() {
    return true;
  }
}
