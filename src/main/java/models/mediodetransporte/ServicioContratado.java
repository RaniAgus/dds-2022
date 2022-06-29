package models.mediodetransporte;

public class ServicioContratado implements MedioDeTransporte {
  private TipoDeServicioContratado tipoDeServicioContratado;

  public ServicioContratado(TipoDeServicioContratado tipoDeServicioContratado) {
    this.tipoDeServicioContratado = tipoDeServicioContratado;
  }

  @Override
  public boolean esCompartible() {
    return true;
  }
}
