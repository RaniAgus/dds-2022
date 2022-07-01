package ar.edu.utn.frba.dds.quemepongo.dependencies.clima;

import java.util.List;

public class CurrentWeather {
  public List<Weather> weather;
  public Main main;

  public static class Weather {
    public String main;
  }

  public static class Main {
    public Double temp;
  }
}
