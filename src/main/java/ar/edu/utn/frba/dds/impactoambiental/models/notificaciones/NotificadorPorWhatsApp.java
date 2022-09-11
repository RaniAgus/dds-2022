package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.HttpRequestException;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Contacto;
import com.google.gson.Gson;
import retrofit2.Response;

import java.io.IOException;

public class NotificadorPorWhatsApp implements Notificador {
  private WhatsAppApi whatsAppApi;
  private String apiKey;
  private String recomendacionesTemplate;

  public NotificadorPorWhatsApp(String phoneNumberId, String apiKey, String recomendacionesTemplate) {
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
