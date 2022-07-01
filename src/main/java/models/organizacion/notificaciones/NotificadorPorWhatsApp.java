package models.organizacion.notificaciones;

import com.mchange.v2.log.slf4j.Slf4jMLog;
import models.organizacion.Contacto;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NotificadorPorWhatsApp implements Notificador {
  private String token;

  public NotificadorPorWhatsApp(String token) {
    this.token = token;
  }

  @Override
  public void enviarGuiaRecomendacion(List<Contacto> contactos, String mensaje) {
    OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    contactos.forEach(unContacto -> {
      String content = String.join("&", Arrays.asList(
          "token=" + token,
          "to=" + unContacto.getTelefono(),
          "body=" + mensaje,
          "priority=10"
      ));
      RequestBody body = RequestBody.create(mediaType, content);
      Request request = new Request.Builder()
          .url("https://api.ultramsg.com/instance10782/messages/chat")
          .post(body)
          .addHeader("content-type", "application/x-www-form-urlencoded")
          .build();
      try (Response response = client.newCall(request).execute()) {
        Slf4jMLog.info(response.toString());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

  }
}
