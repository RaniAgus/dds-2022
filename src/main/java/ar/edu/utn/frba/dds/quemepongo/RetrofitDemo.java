package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.clima.OpenWeather;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.model.notificaciones.Notificador;
import ar.edu.utn.frba.dds.quemepongo.model.notificaciones.NotificadorWhatsApp;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;
import com.google.common.collect.ImmutableList;
import io.github.cdimascio.dotenv.Dotenv;

public class RetrofitDemo {
  private static Dotenv env = Dotenv.load();

  public static void main(String[] args) {
    ServicioMeteorologico s = new OpenWeather(env.get("OPEN_WEATHER_API_KEY"));
    System.out.println("Temperatura:" + s.getTemperatura());
    System.out.println("Alertas:" + s.getAlertas());

    Notificador n = new NotificadorWhatsApp(env.get("WHATSAPP_ID"), env.get("WHATSAPP_API_KEY"));
    n.enviarMensaje(usuario(args[0]), "¡Hola mundo!");

    System.exit(0);
  }

  private static Usuario usuario(String numero) {
    return new Usuario("", numero, ImmutableList.of(), ImmutableList.of(), ImmutableList.of());
  }
}
