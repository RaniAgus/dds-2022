package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class TramoPrivado extends Tramo {
  @Transient //@TODO
  private Ubicacion ubicacionInicial;
  @Transient //@TODO
  private Ubicacion ubicacionFinal;
  @Embedded
  private Distancia distancia;
  @OneToOne(targetEntity = MedioDeTransporte.class)
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
  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return this.medioDeTransporte.tieneTipoDeConsumo(tipo);
  }
}
