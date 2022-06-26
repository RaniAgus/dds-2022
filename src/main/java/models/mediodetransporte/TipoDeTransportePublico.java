package models.mediodetransporte;

public enum TipoDeTransportePublico {
  TREN, SUBTE, COLECTIVO;

  private Double consumoPorKM;
  private TipoDeCombustible tipoDeCombustible;

  public Double consumoPorKM() {
    return consumoPorKM;
  }

  public void setConsumoPorKM(Double combustiblePorKM) {
    this.consumoPorKM = combustiblePorKM;
  }

  public TipoDeCombustible getTipoDeCombustible() {
    return tipoDeCombustible;
  }

  public void setTipoDeCombustible(TipoDeCombustible tipoDeCombustible) {
    this.tipoDeCombustible = tipoDeCombustible;
  }

  public Double carbonoEquivalentePorKM(){
    return tipoDeCombustible.factorEmision() * consumoPorKM;
  }
}
