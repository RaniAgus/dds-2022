package ar.edu.utn.frba.dds.quemepongo.model.notificaciones;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import com.google.common.collect.ImmutableMap;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TemplateEngine {
  private PebbleEngine engine = new PebbleEngine.Builder().build();
  private Map<Alerta, String> mensajes;

  public TemplateEngine(Map<Alerta, String> mensajes) {
    this.mensajes = mensajes;
  }

  public String obtenerHtmlAlertas(Set<Alerta> alertas) {
    PebbleTemplate compiledTemplate = engine.getTemplate("templates/alertas.peb");
    Writer writer = new StringWriter();
    try {
      compiledTemplate.evaluate(writer, ImmutableMap.of(
          "titulo", "¡Hay nuevas alertas meteorológicas!",
          "alertas", alertas.stream()
              .map(it -> ImmutableMap.of(
                  "tipo", it.toString(),
                  "mensaje", mensajes.get(it),
                  "imagen", "cid:" + it + ".png"
              ))
              .collect(Collectors.toList())
      ));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return writer.toString();
  }
}
