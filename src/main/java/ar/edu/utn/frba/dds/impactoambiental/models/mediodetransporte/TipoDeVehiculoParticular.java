package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;

import javax.persistence.Entity;

@Entity
public class TipoDeVehiculoParticular extends EntidadPersistente {
  private String nombre;
  private Double consumoPorKM;

  public String getNombre() {
    return nombre;
  }

  public Double consumoPorKM() {
    return consumoPorKM;
  }

  public void setConsumoPorKM(Double consumoPorKM) {
    this.consumoPorKM = consumoPorKM;
  }
}
