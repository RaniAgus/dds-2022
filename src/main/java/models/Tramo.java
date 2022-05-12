package models;

public class Tramo {
  private Ubicacion ubicacionInicial;
  private Ubicacion ubicacionFinal;
  private MedioDeTransporte medioDeTransporte;

  public Tramo(Ubicacion ubicacionInicial, Ubicacion ubicacionFinal, MedioDeTransporte medioDeTransporte) {
    this.ubicacionInicial = ubicacionInicial;
    this.ubicacionFinal = ubicacionFinal;
    this.medioDeTransporte = medioDeTransporte;
  }
}