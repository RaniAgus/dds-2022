package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

// TODO: quitar esto
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
