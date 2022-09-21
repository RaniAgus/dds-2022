package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

public enum TipoDeTransporte {
  BICICLETA_O_PIE(false),
  TRANSPORTE_PUBLICO(false),
  SERVICIO_CONTRATADO(true),
  VEHICULO_PARTICULAR(true);

  private final boolean compartible;

  TipoDeTransporte(boolean compartible) {
    this.compartible = compartible;
  }

  public boolean esCompartible() {
    return compartible;
  }
}
