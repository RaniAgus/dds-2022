package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.clima.OpenWeather;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import io.github.cdimascio.dotenv.Dotenv;

public class QueMePongoApp {
  private static Dotenv env = Dotenv.load();

  public static void main(String[] args) {
    ServicioMeteorologico clima = new OpenWeather(env.get("OPEN_WEATHER_API_KEY"));
    System.out.println(clima.getTemperatura());
  }
}
