package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public enum TipoDeVehiculoParticular {
  AUTOMOVIL, CAMIONETA, MOTOCICLETA;
  private Double consumoPorKM;
  @Id
  @GeneratedValue
  private long id;

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
