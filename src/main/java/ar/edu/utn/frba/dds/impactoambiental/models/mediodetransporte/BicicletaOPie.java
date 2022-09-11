package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

public class BicicletaOPie implements MedioDeTransporte {
  @Override
  public Double carbonoEquivalentePorKM(){
    return 0.0;
  }

  @Override
  public boolean esCompartible() {
    return false;
  }
}
