package ar.edu.utn.frba.dds.quemepongo.model.clima;

public class Weather {
  private Main main;

  public Weather(Main main) {
    this.main = main;
  }

  public Main getMain() {
    return main;
  }

  public static class Main {
    private Double temp;

    public Main(Double temp) {
      this.temp = temp;
    }

    public Double getTemp() {
      return temp;
    }
  }
}
