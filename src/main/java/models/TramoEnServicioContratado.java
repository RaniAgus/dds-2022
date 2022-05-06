package models;

public class TramoEnServicioContratado extends Tramo{
  private TipoDeServicioContratado tipoDeServicioContratado;

  public TramoEnServicioContratado(Ubicacion ubicacionInicial,
                                   Ubicacion ubicacionFinal,
                                   TipoDeServicioContratado tipoDeServicioContratado) {
    super(ubicacionInicial, ubicacionFinal);
    this.tipoDeServicioContratado = tipoDeServicioContratado;
  }

}
