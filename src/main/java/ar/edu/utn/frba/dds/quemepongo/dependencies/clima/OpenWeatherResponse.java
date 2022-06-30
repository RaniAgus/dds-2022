package ar.edu.utn.frba.dds.quemepongo.dependencies.clima;

public class OpenWeatherResponse {
  public Weather weather;
  public Main main;

  public static class Weather {
    public String main;
  }

  public static class Main {
    public Double temp;
  }
}
