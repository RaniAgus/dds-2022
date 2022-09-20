package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;

@Entity
public class BicicletaOPie extends MedioDeTransporte {
  @Override
  public Double carbonoEquivalentePorKM(){
    return 0.0;
  }

  @Override
  public boolean esCompartible() {
    return false;
  }

  @Override
  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return false;
  }

  public BicicletaOPie() {
    super();
  }
}
