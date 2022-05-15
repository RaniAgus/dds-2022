package models.mediodetransporte;

public class TransportePublico implements MedioDeTransporte {
  private Linea linea;

  public TransportePublico(Linea linea) {
    this.linea = linea;
  }

}
