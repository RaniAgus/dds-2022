package ar.edu.utn.frba.dds.quemepongo.model.clima;

import ar.edu.utn.frba.dds.quemepongo.exception.ServicioMeteorologicoException;
import ar.edu.utn.frba.dds.quemepongo.dependencies.clima.OpenWeatherApi;
import ar.edu.utn.frba.dds.quemepongo.dependencies.clima.OpenWeatherResponse;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class OpenWeather implements ServicioMeteorologico {
  private OpenWeatherApi api;
  private String key;

  public OpenWeather(OpenWeatherApi api, String key) {
    this.api = api;
    this.key = key;
  }

  @Override
  public Temperatura getTemperatura() {
    return Temperatura.ofCelsius(getWeather().main.temp);
  }

  @Override
  public Set<Alerta> getAlertas() {
    Map<String, Alerta> alertas = ImmutableMap.of(
        "Rain", Alerta.TORMENTA,
        "Thunderstorm", Alerta.TORMENTA,
        "Hail", Alerta.GRANIZO
    );

    return Optional.ofNullable(alertas.get(getWeather().weather.main))
        .map(ImmutableSet::of)
        .orElse(ImmutableSet.of());
  }

  private OpenWeatherResponse getWeather() {
    try {
      return Optional
          .ofNullable(api.getWeather("Buenos Aires,ar", "metric", key).execute().body())
          .orElseThrow(ServicioMeteorologicoException::new);
    } catch (IOException e) {
      throw new ServicioMeteorologicoException(e);
    }
  }
}
