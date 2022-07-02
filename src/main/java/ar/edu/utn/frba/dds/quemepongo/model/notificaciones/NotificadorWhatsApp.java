package ar.edu.utn.frba.dds.quemepongo.model.notificaciones;

import ar.edu.utn.frba.dds.quemepongo.dependencies.notificaciones.TextMessage;
import ar.edu.utn.frba.dds.quemepongo.dependencies.notificaciones.WhatsAppApi;
import ar.edu.utn.frba.dds.quemepongo.exception.NotificadorException;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;
import java.io.IOException;
import java.util.Optional;

public class NotificadorWhatsApp implements Notificador {
  private WhatsAppApi whatsAppApi;
  private String apiKey;

  public NotificadorWhatsApp(String phoneNumberId, String apiKey) {
    this.whatsAppApi = WhatsAppApi.create(phoneNumberId);
    this.apiKey = apiKey;
  }

  @Override
  public void enviarMensaje(Usuario usuario, String mensaje) {
    try {
      Optional.ofNullable(
          whatsAppApi.sendTextMessage(
              new TextMessage(usuario.getTelefono(), mensaje),
              "Bearer " + apiKey
          ).execute().body()
      ).orElseThrow(() -> new NotificadorException(mensaje));
    } catch (IOException e) {
      throw new NotificadorException(mensaje, e);
    }
  }
}
