package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;

import javax.persistence.*;

@Entity
public class Parada extends EntidadPersistente {
  private String nombre;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "valor", column = @Column(name = "distancia_anterior_parada")),
      @AttributeOverride(name = "unidad", column = @Column(name = "unidad_anterior_parada"))
  })
  private Distancia distanciaAAnteriorParada;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "valor", column = @Column(name = "distancia_proxima_parada")),
      @AttributeOverride(name = "unidad", column = @Column(name = "unidad_proxima_parada"))
  })
  private Distancia distanciaAProximaParada;

  protected Parada() {
  }

  public Parada(String nombre, Distancia distanciaAAnteriorParada, Distancia distanciaAProximaParada) {
    this.nombre = nombre;
    this.distanciaAAnteriorParada = distanciaAAnteriorParada;
    this.distanciaAProximaParada = distanciaAProximaParada;
  }

  public Distancia getDistanciaAProximaParada() {
    return distanciaAProximaParada;
  }

  public Distancia getDistanciaAAnteriorParada() {
    return distanciaAAnteriorParada;
  }

  public void setDistanciaAProximaParada(Distancia distanciaAProximaParada) {
    this.distanciaAProximaParada = distanciaAProximaParada;
  }

  public void setDistanciaAAnteriorParada(Distancia distanciaAAnteriorParada) {
    this.distanciaAAnteriorParada = distanciaAProximaParada;
  }
}
