package models.organizacion.notificaciones;

import models.organizacion.Contacto;
import okhttp3.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class WppDelivered implements ServicioDeNotificacion{
  @Override
  public void enviarGuiaRecomendacion(List<Contacto> contactos, String mensaje) throws MessagingException, IOException {
    OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    contactos.forEach(unContacto -> {
      RequestBody body = RequestBody.create(mediaType, "token=oley5l6hjtsfzdvt&to="+unContacto.getTelefono()+"&body="+mensaje+"&priority=10");
      Request request = new Request.Builder()
          .url("https://api.ultramsg.com/instance10782/messages/chat")
          .post(body)
          .addHeader("content-type", "application/x-www-form-urlencoded")
          .build();
      try {
        Response response = client.newCall(request).execute();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

  }
}
