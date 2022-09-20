package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ServicioContratado extends MedioDeTransporte {
  @OneToOne(targetEntity = TipoDeServicioContratado.class)
  private TipoDeServicioContratado tipoDeServicioContratado;

  public ServicioContratado(TipoDeServicioContratado tipoDeServicioContratado) {
    this.tipoDeServicioContratado = tipoDeServicioContratado;
  }

  public ServicioContratado() {
  }

  @Override
  public Double carbonoEquivalentePorKM() {
    return tipoDeServicioContratado.carbonoEquivalentePorKM();
  }
  
  @Override
  public boolean esCompartible() {
    return true;
  }

  @Override
  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return tipoDeServicioContratado.tieneTipoDeConsumo(tipo);
  }
}
