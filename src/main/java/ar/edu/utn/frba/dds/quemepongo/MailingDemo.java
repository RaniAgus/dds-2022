package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.notificaciones.Mailer;
import ar.edu.utn.frba.dds.quemepongo.model.notificaciones.MailerJava;
import ar.edu.utn.frba.dds.quemepongo.model.notificaciones.TemplateEngine;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

public class MailingDemo {
  private static Dotenv env = Dotenv.load();
  private static Map<Alerta, String> mensajes = ImmutableMap.of(
    Alerta.TORMENTA, "¡No te olvides de salir con paraguas!",
    Alerta.GRANIZO, "¡Evitá salir en auto!"
  );

  public static void main(String[] args) {
    Mailer mailer = new MailerJava(
        env.get("MAILER_USER"),
        env.get("MAILER_PASSWORD"),
        new TemplateEngine(mensajes)
    );
    mailer.enviarAlertas(args[0], ImmutableSet.of(Alerta.TORMENTA, Alerta.GRANIZO));
  }
}
