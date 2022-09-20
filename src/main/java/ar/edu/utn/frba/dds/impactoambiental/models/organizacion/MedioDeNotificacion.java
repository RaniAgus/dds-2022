package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Notificador;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.NotificadorPorMail;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.NotificadorPorWhatsApp;

public enum MedioDeNotificacion {
  WHATSAPP() {
    @Override
    protected Notificador getNotificador() {
      return NotificadorPorWhatsApp.INSTANCE;
    }
  },
  MAIL() {
    @Override
    protected Notificador getNotificador() {
      return NotificadorPorMail.INSTANCE;
    }
  };

  public void enviarGuiaRecomendacion(Contacto contacto, String link) {
    getNotificador().enviarGuiaRecomendacion(contacto, link);
  }

  protected abstract Notificador getNotificador();
}
