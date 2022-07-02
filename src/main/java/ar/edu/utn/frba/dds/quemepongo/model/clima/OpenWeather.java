package ar.edu.utn.frba.dds.quemepongo.model.clima;

import ar.edu.utn.frba.dds.quemepongo.exception.ServicioMeteorologicoException;
import ar.edu.utn.frba.dds.quemepongo.dependencies.clima.OpenWeatherApi;
import ar.edu.utn.frba.dds.quemepongo.dependencies.clima.CurrentWeather;
import com.google.common.collect.ImmutableMap;
import retrofit2.Call;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OpenWeather implements ServicioMeteorologico {
  private OpenWeatherApi api = OpenWeatherApi.create();
  private String key;

  public OpenWeather(String key) {
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

    return getWeather().weather.stream()
        .map(it -> Optional.ofNullable(alertas.get(it.main)))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
  }

  private CurrentWeather getWeather() {
    try {
      Call<CurrentWeather> call = api.getCurrentWeather("Buenos Aires,ar", "metric", key);
      return Optional.ofNullable(call.execute().body())
          .orElseThrow(ServicioMeteorologicoException::new);
    } catch (IOException e) {
      throw new ServicioMeteorologicoException(e);
    }
  }
}
