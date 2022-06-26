package models.mediodetransporte;

public enum TipoDeVehiculoParticular {
  AUTOMOVIL, CAMIONETA, MOTOCICLETA;

  private Double consumoPorKM;

  public Double consumoPorKM() {
    return consumoPorKM;
  }

  public void setConsumoPorKM(Double consumoPorKM) {
    this.consumoPorKM = consumoPorKM;
  }
}
