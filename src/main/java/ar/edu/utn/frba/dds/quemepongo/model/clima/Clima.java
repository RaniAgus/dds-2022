package ar.edu.utn.frba.dds.quemepongo.model.clima;

import java.time.LocalDateTime;
import java.util.Set;

public class Clima implements Comparable<LocalDateTime> {
  private Temperatura temperatura;
  private Set<Alerta> alertas;
  private LocalDateTime horaDeActualizacion;

  public Clima(Temperatura temperatura,
               Set<Alerta> alertas,
               LocalDateTime horaDeActualizacion) {
    this.temperatura = temperatura;
    this.alertas = alertas;
    this.horaDeActualizacion = horaDeActualizacion;
  }

  public Temperatura getTemperatura() {
    return temperatura;
  }

  public Set<Alerta> getAlertas() {
    return alertas;
  }

  public boolean tieneAlertas() {
    return !alertas.isEmpty();
  }

  public boolean tieneAlerta(Alerta alerta) {
    return alertas.contains(alerta);
  }

  @Override
  public int compareTo(LocalDateTime localDateTime) {
    return horaDeActualizacion.compareTo(localDateTime);
  }
}
