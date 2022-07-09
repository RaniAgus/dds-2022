package models.da;

import java.time.Period;

public enum Periodicidad {
  MENSUAL{
    public Integer diasLaborales() {
      return 20;
    }

    public Period getPeriodo() {
      return Period.ofMonths(1);
    }
  },
  ANUAL{
    public Integer diasLaborales() {
      return 20*12;
    }

    public Period getPeriodo() {
      return Period.ofYears(1);
    }
  };

  public abstract Integer diasLaborales();
  public abstract Period getPeriodo();
}
