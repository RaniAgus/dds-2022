package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.dependencies.notificaciones.NotificationService;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.Map;
import java.util.Set;

public class NotificarAlertas implements AccionTrasAlertas {
  private NotificationService notificationService;
  private Map<Alerta, String> mensajes;

  public NotificarAlertas(NotificationService notificationService,
                          Map<Alerta, String> mensajes) {
    this.notificationService = notificationService;
    this.mensajes = mensajes;
  }

  @Override
  public void anteNuevasAlertas(Usuario usuario, Set<Alerta> alertas) {
    alertas.forEach(it -> notificationService.notify(mensajes.get(it)));
  }
}
