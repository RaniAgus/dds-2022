public abstract class Tramo {
  private Ubicacion ubicacionInicial;
  private Ubicacion ubicacionFinal;

  public Tramo(Ubicacion ubicacionInicial, Ubicacion ubicacionFinal) {
    this.ubicacionInicial = ubicacionInicial;
    this.ubicacionFinal = ubicacionFinal;
  }
}
