package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Geolocalizador;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;

import javax.persistence.*;

@Entity
public class TramoPrivado extends Tramo {
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "idLocalidad", column = @Column(name = "ubicacion_inicial_localidad")),
      @AttributeOverride(name = "calle", column = @Column(name = "ubicacion_inicial_calle")),
      @AttributeOverride(name = "altura", column = @Column(name = "ubicacion_inicial_altura"))
  })
  private final Ubicacion ubicacionInicial;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "idLocalidad", column = @Column(name = "ubicacion_final_localidad")),
      @AttributeOverride(name = "calle", column = @Column(name = "ubicacion_final_calle")),
      @AttributeOverride(name = "altura", column = @Column(name = "ubicacion_final_altura"))
  })
  private final Ubicacion ubicacionFinal;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "valor", column = @Column(name = "distancia_valor")),
      @AttributeOverride(name = "unidad", column = @Column(name = "distancia_unidad")),
  })
  private final Distancia distancia;
  @ManyToOne
  private final MedioDeTransporte medioDeTransporte;

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
