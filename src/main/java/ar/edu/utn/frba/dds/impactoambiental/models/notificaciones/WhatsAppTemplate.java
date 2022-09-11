package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class WhatsAppTemplate {
  private String messaging_product = "whatsapp";
  private String to;
  private String type = "template";
  private Map<String, Object> template;

  public WhatsAppTemplate(String template, String phoneNumber, String link) {
    this.to = phoneNumber;
    this.template = ImmutableMap.of(
        "name", template,
        "language", ImmutableMap.of("code", "es_AR"),
        "components", ImmutableList.of(ImmutableMap.of(
            "type", "button",
            "sub_type", "url",
            "index", 0,
            "parameters", ImmutableList.of(ImmutableMap.of(
                "type", "text",
                "text", link
            ))
        ))
    );
  }
}
