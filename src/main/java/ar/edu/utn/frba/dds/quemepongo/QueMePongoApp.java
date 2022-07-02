package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.tareaprogramada.Planificador;
import ar.edu.utn.frba.dds.quemepongo.model.tareaprogramada.GenerarAlertas;
import ar.edu.utn.frba.dds.quemepongo.model.tareaprogramada.GenerarSugerencias;
import ar.edu.utn.frba.dds.quemepongo.model.clima.OpenWeather;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ProxyWeather;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import io.github.cdimascio.dotenv.Dotenv;
import org.quartz.*;
import java.time.Duration;

public final class QueMePongoApp {
  private static Dotenv env = Dotenv.load();
  private static RepositorioAlertas repositorioAlertas = new RepositorioAlertas();
  private static RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  private static ServicioMeteorologico servicioMeteorologico = new ProxyWeather(
      new OpenWeather(env.get("OPEN_WEATHER_API_KEY")),
      Duration.ofMinutes(1)
  );

  public static void main(String[] args) throws SchedulerException {
    new Planificador()
        .agregarTarea(new GenerarAlertas(repositorioAlertas, repositorioUsuarios, servicioMeteorologico), env.get("ALERTAS_CRON"))
        .agregarTarea(new GenerarSugerencias(repositorioUsuarios), env.get("SUGERENCIAS_CRON"))
        .iniciar();
  }
}
