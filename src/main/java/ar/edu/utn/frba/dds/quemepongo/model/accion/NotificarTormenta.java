package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.dependencies.NotificationService;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.Set;

public class NotificarTormenta implements Accion {
  private NotificationService notificationService;

  public NotificarTormenta(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public void emitirA(Usuario usuario, Set<Alerta> alertas) {
    if (alertas.contains(Alerta.TORMENTA)) {
      notificationService.notify(
          "¡Alerta de tormenta! No te olvides de salir con un paraguas.");
    }
  }
}
