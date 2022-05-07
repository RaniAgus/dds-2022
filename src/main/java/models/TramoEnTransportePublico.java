package models;

public class TramoEnTransportePublico extends Tramo {
  private Linea linea;

  public TramoEnTransportePublico(Ubicacion ubicacionInicial,
                                  Ubicacion ubicacionFinal,
                                  Linea linea) {
    super(ubicacionInicial, ubicacionFinal);
    this.linea = linea;
  }

}
