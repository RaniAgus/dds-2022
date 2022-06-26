package models.mediodetransporte;

public class ServicioContratado implements MedioDeTransporte {
  private TipoDeServicioContratado tipoDeServicioContratado;

  public ServicioContratado(TipoDeServicioContratado tipoDeServicioContratado) {
    this.tipoDeServicioContratado = tipoDeServicioContratado;
  }

  public Double carbonoEquivalentePorKM() {
    return tipoDeServicioContratado.carbonoEquivalentePorKM();
  }
}
