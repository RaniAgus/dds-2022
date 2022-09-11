package ar.edu.utn.frba.dds.impactoambiental.models.da;

import java.time.Period;

public enum Periodicidad {
  MENSUAL{
    public Period getPeriodo() {
      return Period.ofMonths(1).minusDays(1);
    }
  },
  ANUAL{
    public Period getPeriodo() {
      return Period.ofYears(1).minusDays(1);
    }
  };

  public abstract Period getPeriodo();
}
