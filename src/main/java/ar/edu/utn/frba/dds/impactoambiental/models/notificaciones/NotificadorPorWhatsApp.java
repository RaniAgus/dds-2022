package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import static ar.edu.utn.frba.dds.impactoambiental.ServiceLocator.getServiceLocator;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.HttpRequestException;
import com.google.gson.Gson;
import java.io.IOException;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import retrofit2.Response;

@Entity
@DiscriminatorValue("WhatsApp")
public class NotificadorPorWhatsApp extends Notificador {
  @Transient
  private final WhatsAppApi whatsAppApi;
  @Transient
  private final String apiKey;
  @Transient
  private final String recomendacionesTemplate;

  protected NotificadorPorWhatsApp() {
    this(getServiceLocator().getWhatsappApiId(),
        getServiceLocator().getWhatsappApiKey(),
        getServiceLocator().getRecomendacionesTemplate());
  }

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
