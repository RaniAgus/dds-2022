package ar.edu.utn.frba.dds.quemepongo.model.clima;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class ProxyWeather implements ServicioMeteorologico {
  private ServicioMeteorologico servicioMeteorologico;
  private Duration timeout;
  private Temperatura temperatura;
  private Set<Alerta> alertas;
  private LocalDateTime ultimaActualizacion;

  public ProxyWeather(ServicioMeteorologico servicioMeteorologico,
                      Duration timeout) {
    this.servicioMeteorologico = servicioMeteorologico;
    this.timeout = timeout;
  }

  @Override
  public Temperatura getTemperatura() {
    verificarSiDebeActualizar();
    return temperatura;
  }

  @Override
  public Set<Alerta> getAlertas() {
    verificarSiDebeActualizar();
    return alertas;
  }

  private synchronized void verificarSiDebeActualizar() {
    if (debeActualizar()) {
      temperatura = servicioMeteorologico.getTemperatura();
      alertas = servicioMeteorologico.getAlertas();
      ultimaActualizacion = LocalDateTime.now();
    }
  }

  private boolean debeActualizar() {
    return ultimaActualizacion == null
        || LocalDateTime.now().isAfter(ultimaActualizacion.plus(timeout));
  }
}
