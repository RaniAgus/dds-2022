package ar.edu.utn.frba.dds.quemepongo.model.clima;

import ar.edu.utn.frba.dds.quemepongo.exception.TemperaturaNoObtenidaException;
import ar.edu.utn.frba.dds.quemepongo.model.Temperatura;
import com.google.common.collect.ImmutableMap;
import edu.utn.frba.dds.accuweather.AccuWeatherAPI;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class AccuWeather implements ServicioMeteorologico {
  private Temperatura temperatura;
  private LocalDateTime ultimaActualizacion;

  @Override
  public Temperatura getTemperatura() {
    if (climaEstaDesactualizado()) {
      temperatura = actualizarTemperatura();
      ultimaActualizacion = LocalDateTime.now();
    }
    return temperatura;
  }

  private boolean climaEstaDesactualizado() {
    return ultimaActualizacion == null
        || ultimaActualizacion.isBefore(LocalDateTime.now().minusHours(2));
  }

  @SuppressWarnings("unchecked")
  private Temperatura actualizarTemperatura() {
    try {
      Map<String, Object> data = (Map<String, Object>) new AccuWeatherAPI()
          .getWeather("Buenos Aires, Argentina")
          .get(0)
          .get("Temperature");

      Map<String, Function<Double, Temperatura>> converters = ImmutableMap.of(
          "F", Temperatura::ofFahrenheit,
          "C", Temperatura::ofCelsius
      );

      return Objects.requireNonNull(converters.get((String) data.get("Unit")))
          .apply(Double.valueOf((Integer) data.get("Value")));
    } catch (NullPointerException e) {
      throw new TemperaturaNoObtenidaException(e);
    }
  }
}
