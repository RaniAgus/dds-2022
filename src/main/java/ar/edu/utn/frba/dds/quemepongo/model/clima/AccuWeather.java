package ar.edu.utn.frba.dds.quemepongo.model.clima;

import ar.edu.utn.frba.dds.quemepongo.model.Temperatura;
import edu.utn.frba.dds.accuweather.AccuWeatherAPI;

import java.time.LocalDateTime;
import java.util.Map;

public class AccuWeather implements ServicioMeteorologico {
  public static final AccuWeather INSTANCE = new AccuWeather();

  private Temperatura temperatura;
  private LocalDateTime ultimaActualizacion;

  private AccuWeather() {
  }

  @Override
  public Temperatura getTemperatura() {
    if (climaEstaDesctualizado()) {
      actualizarClima();
    }
    return temperatura;
  }

  private boolean climaEstaDesctualizado() {
    return ultimaActualizacion == null
        || ultimaActualizacion.isBefore(LocalDateTime.now().minusHours(12));
  }

  @SuppressWarnings("unchecked")
  private void actualizarClima() {
    Map<String, Object> data = (Map<String, Object>) new AccuWeatherAPI()
        .getWeather("Buenos Aires, Argentina")
        .get(0)
        .get("Temperature");

    temperatura = Temperatura.of((String) data.get("Unit"), (Integer) data.get("Value"));
    ultimaActualizacion = LocalDateTime.now();
  }
}
