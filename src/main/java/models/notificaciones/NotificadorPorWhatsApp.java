package models.notificaciones;

import models.organizacion.Contacto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class NotificadorPorWhatsApp implements Notificador {
  private WhatsAppApi whatsAppApi;
  private String apiKey;

  public NotificadorPorWhatsApp(String phoneNumberId, String apiKey) {
    this.whatsAppApi = WhatsAppApi.create(phoneNumberId);
    this.apiKey = apiKey;
  }

  @Override
  public void enviarGuiaRecomendacion(Contacto contacto, String link) {
    enviarMensaje(
        contacto,
        "Conocé nuestras últimas recomendaciones sobre cómo reducir el impacto ambiental de tu organización: "
            + link
    );
  }

  private void enviarMensaje(Contacto contacto, String mensaje) {
    try {
      Optional.ofNullable(
          whatsAppApi.sendTextMessage(
              new TextMessage(contacto.getTelefono(), mensaje),
              "Bearer " + apiKey
          ).execute().body()
      ).orElseThrow(() -> new RuntimeException("No se pudo enviar el mensaje: " + mensaje));
    } catch (IOException e) {
      throw new RuntimeException("No se pudo enviar el mensaje: " + mensaje, e);
    }
  }
}
