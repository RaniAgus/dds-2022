package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.HttpRequestException;
import com.google.gson.Gson;
import retrofit2.Response;

import java.io.IOException;

public class NotificadorPorWhatsApp implements Notificador {
  public static final NotificadorPorWhatsApp INSTANCE = new NotificadorPorWhatsApp(
      System.getenv("WHATSAPP_ID"),
      System.getenv("WHATSAPP_API_KEY"),
      System.getenv("RECOMENDACIONES_TEMPLATE")
  );

  private final WhatsAppApi whatsAppApi;
  private final String apiKey;
  private final String recomendacionesTemplate;

  private NotificadorPorWhatsApp(String phoneNumberId, String apiKey, String recomendacionesTemplate) {
    this.whatsAppApi = WhatsAppApi.create(phoneNumberId);
    this.apiKey = apiKey;
    this.recomendacionesTemplate = recomendacionesTemplate;
  }

  @Override
  public void enviarGuiaRecomendacion(Contacto contacto, String link) {
    WhatsAppTemplate template = new WhatsAppTemplate(recomendacionesTemplate, contacto.getTelefono(), link);
    try {
      Response<?> response = whatsAppApi
          .sendTemplate(template, "Bearer " + apiKey)
          .execute();

      if (response.body() == null) {
        throw new HttpRequestException(new Gson()
            .fromJson(response.errorBody().charStream(), WhatsAppError.class)
            .getErrorMessage());
      }
    } catch (IOException e) {
      throw new HttpRequestException("Ocurrió un error al interactuar con WhatsApp API", e);
    }
  }
}
