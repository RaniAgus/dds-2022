package ar.edu.utn.frba.dds.quemepongo.model.clima;

import ar.edu.utn.frba.dds.quemepongo.exception.TemperaturaNoObtenidaException;
import ar.edu.utn.frba.dds.quemepongo.model.Temperatura;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class OpenWeather implements ServicioMeteorologico {
  public static final OpenWeather INSTANCE = new OpenWeather();

  private OpenWeatherApi api = new Retrofit.Builder()
      .baseUrl("https://api.openweathermap.org/data/2.5/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(OpenWeatherApi.class);
  private String key = System.getenv("OPENWEATHER");
  private Temperatura temperatura;
  private LocalDateTime ultimaActualizacion;

  private OpenWeather() {
  }

  @Override
  public Temperatura getTemperatura() {
    if (climaEstaDesctualizado()) {
      temperatura = actualizarTemperatura();
      ultimaActualizacion = LocalDateTime.now();
    }
    return temperatura;
  }

  private boolean climaEstaDesctualizado() {
    return ultimaActualizacion == null
        || ultimaActualizacion.isBefore(LocalDateTime.now().minusHours(1));
  }

  private Temperatura actualizarTemperatura() {
    Weather weather;
    try {
      weather = Optional.ofNullable(
          api.getWeather("Buenos Aires,ar", "metric", key)
              .execute()
              .body()
      ).orElseThrow(TemperaturaNoObtenidaException::new);
    } catch (IOException e) {
      throw new TemperaturaNoObtenidaException(e);
    }

    return Temperatura.ofCelsius(weather.getMain().getTemp());
  }
}
