package ar.edu.utn.frba.dds.quemepongo.model.clima;

import ar.edu.utn.frba.dds.quemepongo.exception.TemperaturaNoObtenidaException;
import ar.edu.utn.frba.dds.quemepongo.model.Temperatura;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class OpenWeather implements ServicioMeteorologico {
  private String key;
  private Temperatura temperatura;
  private LocalDateTime ultimaActualizacion;

  public OpenWeather(String key) {
    this.key = key;
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
          OpenWeatherApi.INSTANCE.getWeather("Buenos Aires,ar", "metric", key)
              .execute()
              .body()
      ).orElseThrow(TemperaturaNoObtenidaException::new);
    } catch (IOException e) {
      throw new TemperaturaNoObtenidaException(e);
    }

    return Temperatura.ofCelsius(weather.getMain().getTemp());
  }
}
