package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.jobs.GeneradorDeAlertas;
import ar.edu.utn.frba.dds.quemepongo.jobs.GeneradorDeSugerencias;
import ar.edu.utn.frba.dds.quemepongo.model.clima.OpenWeather;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import io.github.cdimascio.dotenv.Dotenv;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public final class QueMePongoApp {
  private static Dotenv env = Dotenv.load();
  private static ServicioMeteorologico servicioMeteorologico = new OpenWeather(
      env.get("OPEN_WEATHER_API_KEY")
  );

  public static void main(String[] args) throws SchedulerException {
    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.getContext().put("servicioMeteorologico", servicioMeteorologico);
    scheduler.scheduleJob(
        crearJobDetail(GeneradorDeAlertas.class),
        crearTrigger(new Date(), 3)
    );
    scheduler.scheduleJob(
        crearJobDetail(GeneradorDeSugerencias.class),
        crearTrigger(calcularHora(4, 0), 24)
    );
    scheduler.start();
  }

  private static JobDetail crearJobDetail(Class<? extends Job> clazz) {
    return newJob(clazz)
        .withIdentity(clazz.getName(), "QueMePongoApp")
        .build();
  }

  private static Trigger crearTrigger(Date horaInicio, int frecuenciaEnHoras) {
    return newTrigger()
        .withIdentity(UUID.randomUUID().toString())
        .startAt(horaInicio)
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInHours(frecuenciaEnHoras)
            .repeatForever())
        .build();
  }

  public static Date calcularHora(int horas, int minutos) {
    return Date.from(
        ZonedDateTime.now()
            .withHour(horas)
            .withMinute(minutos)
            .toInstant()
    );
  }
}
