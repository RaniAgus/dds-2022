package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TramoEnTransportePublico extends Tramo {
  @ManyToOne
  private final Parada paradaInicial;
  @ManyToOne
  private final Parada paradaFinal;
  @ManyToOne
  private final Linea linea;

  public TramoEnTransportePublico(Parada paradaInicial, Parada paradaFinal, Linea linea) {
    this.paradaInicial = paradaInicial;
    this.paradaFinal = paradaFinal;
    this.linea = linea;
  }

  @Override
  public Distancia getDistancia() {
    return linea.distanciaEntreParadas(paradaInicial, paradaFinal);
  }

  @Override
  public boolean esCompartible() {
    return false;
  }

  public Double carbonoEquivalente() {
    return linea.consumoEntreParadas(paradaInicial, paradaFinal);
  }

  @Override
  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return this.linea.tieneTipoDeConsumo(tipo);
  }
}
