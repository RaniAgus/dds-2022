package models.mediodetransporte;

public class BicicletaOPie implements MedioDeTransporte {

  @Override
  public boolean esCompartible() {
    return false;
  }
}
