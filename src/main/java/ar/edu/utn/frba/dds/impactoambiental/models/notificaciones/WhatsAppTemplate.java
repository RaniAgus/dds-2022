package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class WhatsAppTemplate {
  private final String messaging_product = "whatsapp";
  private final String to;
  private final String type = "template";
  private final Map<String, Object> template;

  public WhatsAppTemplate(String templateName, String phoneNumber, String relativeUrl) {
    this.to = phoneNumber;
    this.template = ImmutableMap.of(
        "name", templateName,
        "language", ImmutableMap.of("code", "es_AR")
    );
  }
}
