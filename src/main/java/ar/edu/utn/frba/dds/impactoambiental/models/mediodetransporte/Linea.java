package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;

import javax.persistence.*;
import java.util.List;
@Entity
public class Linea {
  @Id
  @GeneratedValue
  private long id;
  private String nombre;
  @OneToMany
  private List<Parada> paradas;
  @Transient //@TODO
  private TipoDeTransportePublico tipo;

  public Linea(String nombre, List<Parada> paradas, TipoDeTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = paradas;
    this.tipo = tipo;
  }

  public void agregarParada(Parada nuevaParada, Distancia distanciaANuevaParada) {
    if (!paradas.isEmpty()) {
      paradas.get(paradas.size() - 1).setDistanciaAProximaParada(distanciaANuevaParada);
    }
    this.paradas.add(nuevaParada);
  }

  public List<Parada> getParadas() {
    return paradas;
  }

  public Distancia distanciaEntreParadas(Parada paradaInicial, Parada paradaFinal) {
    int posInicial = paradas.indexOf(paradaInicial);
    int posFinal = paradas.indexOf(paradaFinal);

    if (posInicial < posFinal) {
      return distanciaParadasIda(posInicial, posFinal);
    }

    return distanciaParadasVuelta(posInicial, posFinal);
  }

  private Distancia distanciaParadasIda(int paradaInicial, int paradaFinal) {
    return paradas.subList(paradaInicial, paradaFinal)
        .stream()
        .map(Parada::getDistanciaAProximaParada)
        .reduce(Distancia.CERO, Distancia::sumar);
  }

  private Distancia distanciaParadasVuelta(int paradaInicial, int paradaFinal) {
    return paradas.subList(paradaFinal, paradaInicial)
        .stream()
        .map(Parada::getDistanciaAAnteriorParada)
        .reduce(Distancia.CERO, Distancia::sumar);
  }

  public Double consumoEntreParadas(Parada paradaInicial, Parada paradaFinal) {
    return distanciaEntreParadas(paradaInicial, paradaFinal).getValor() * tipo.carbonoEquivalentePorKM();
  }

  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return this.tipo.tieneTipoDeConsumo(tipo);
  }
}
