package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

public class ServicioContratado implements MedioDeTransporte {
  private TipoDeServicioContratado tipoDeServicioContratado;

  public ServicioContratado(TipoDeServicioContratado tipoDeServicioContratado) {
    this.tipoDeServicioContratado = tipoDeServicioContratado;
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
