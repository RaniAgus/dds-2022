package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

public enum TipoDeVehiculoParticular {
  AUTOMOVIL, CAMIONETA, MOTOCICLETA;
  private Double consumoPorKM;

  public Double consumoPorKM() {
    if(consumoPorKM == null) {
      throw new IllegalStateException("El dato de consumo por KM para " + this.toString() + " no fue cargado.");
    }
    return consumoPorKM;
  }

  public void setConsumoPorKM(Double consumoPorKM) {
    this.consumoPorKM = consumoPorKM;
  }
}
