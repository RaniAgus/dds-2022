package ar.edu.utn.frba.dds.quemepongo.model.clima;

import java.util.Set;

public interface ServicioMeteorologico {
  Temperatura getTemperatura();
  Set<Alerta> getAlertas();
}
