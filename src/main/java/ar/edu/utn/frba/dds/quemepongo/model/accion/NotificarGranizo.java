package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.dependencies.NotificationService;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import ar.edu.utn.frba.dds.quemepongo.model.guardarropas.Usuario;

public class NotificarGranizo implements Accion {
  private NotificationService notificationService;

  public NotificarGranizo(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public void emitirA(Usuario usuario, Clima clima) {
    if (clima.tieneAlerta(Alerta.GRANIZO)) {
      notificationService.notify(
          "¡Alerta de granizo! Recomendamos evitar salir en auto.");
    }
  }
}
