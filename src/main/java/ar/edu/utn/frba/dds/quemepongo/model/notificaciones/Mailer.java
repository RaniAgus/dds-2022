package ar.edu.utn.frba.dds.quemepongo.model.notificaciones;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import java.util.Set;

public interface Mailer {
  void enviarAlertas(String email, Set<Alerta> alertas);
}
