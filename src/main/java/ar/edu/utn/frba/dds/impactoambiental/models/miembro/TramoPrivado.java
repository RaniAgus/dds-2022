package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;

public class TramoPrivado implements Tramo {
  private Ubicacion ubicacionInicial;
  private Ubicacion ubicacionFinal;
  private Distancia distancia;
  private MedioDeTransporte medioDeTransporte;

  public TramoPrivado(Geolocalizador geolocalizador,
                      Ubicacion ubicacionInicial,
                      Ubicacion ubicacionFinal,
                      MedioDeTransporte medioDeTransporte) {
    this.ubicacionFinal = ubicacionFinal;
    this.ubicacionInicial = ubicacionInicial;
    this.distancia = geolocalizador.medirDistancia(ubicacionInicial, ubicacionFinal);
    this.medioDeTransporte = medioDeTransporte;
  }

  @Override
  public Distancia getDistancia() {
    return distancia;
  }

  @Override
  public boolean esCompartible() {
    return medioDeTransporte.esCompartible();
  }

  public Double carbonoEquivalente() {
    return medioDeTransporte.carbonoEquivalentePorKM() * distancia.getValor();
  }

  @Override
  public TipoDeConsumo getTipoDeConsumo() {
    return this.medioDeTransporte.getTipoDeConsumo();
  }
}
