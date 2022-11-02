package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class TramoEnTransportePublico extends Tramo {
  @ManyToOne
  private Parada paradaInicial;
  @ManyToOne
  private Parada paradaFinal;
  @ManyToOne
  private Linea linea;

  public TramoEnTransportePublico(Parada paradaInicial, Parada paradaFinal, Linea linea) {
    this.paradaInicial = paradaInicial;
    this.paradaFinal = paradaFinal;
    this.linea = linea;
  }

  public TramoEnTransportePublico() {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TramoEnTransportePublico that = (TramoEnTransportePublico) o;
    return Objects.equals(paradaInicial, that.paradaInicial) && Objects.equals(paradaFinal, that.paradaFinal) && Objects.equals(linea, that.linea);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paradaInicial, paradaFinal, linea);
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

  @Override
  public String nombreOrigen() {
    return paradaInicial.getNombre();
  }

  @Override
  public String nombreDestino() {
    return paradaFinal.getNombre();
  }

  @Override
  public String nombreMedio() {
    return linea.getNombre();
  }

  @Override
  public String tipo() {
    return "Publico";
  }
}
