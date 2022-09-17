package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Notificador;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.NotificadorPorMail;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.NotificadorPorWhatsApp;

public enum MedioDeNotificacion {
  WHATSAPP(NotificadorPorWhatsApp.INSTANCE),
  MAIL(NotificadorPorMail.INSTANCE);

  private Notificador notificador;

  MedioDeNotificacion(Notificador notificador) {
    this.notificador = notificador;
  }

  public void enviarGuiaRecomendacion(Contacto contacto, String link) {
    notificador.enviarGuiaRecomendacion(contacto, link);
  }
}
