package models.mediodetransporte;

public class TipoDeServicioContratado {
  private String nombre;
  private TipoDeCombustible tipoDeCombustible;
  private Double consumoPorKM;

  public TipoDeServicioContratado(String nombre, 
                                  TipoDeCombustible tipoDeCombustible,
                                  Double consumoPorKM) {
    this.nombre = nombre;
    this.tipoDeCombustible = tipoDeCombustible;
    this.consumoPorKM = consumoPorKM;
  }

  public static final TipoDeServicioContratado TAXI = new TipoDeServicioContratado("Taxi", TipoDeCombustible.NAFTA, 1.0);
  public static final TipoDeServicioContratado REMIS = new TipoDeServicioContratado("Remis", TipoDeCombustible.NAFTA, 1.0);

  public Double carbonoEquivalentePorKM() {
    return tipoDeCombustible.factorEmision() * consumoPorKM;
  }
}
