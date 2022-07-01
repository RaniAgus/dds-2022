package models.notificaciones;

import models.organizacion.Organizacion;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerEnviarGuias {
  private LocalTime horaInicio;
  private Duration intervalo;
  private String link;
  private List<Organizacion> organizaciones;

  public SchedulerEnviarGuias(LocalTime horaInicio,
                              Duration intervalo,
                              String link,
                              List<Organizacion> organizaciones) {
    this.horaInicio = horaInicio;
    this.intervalo = intervalo;
    this.link = link;
    this.organizaciones = organizaciones;
  }

  public Scheduler ejecutar() throws SchedulerException {
    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.scheduleJob(crearJob(), crearTrigger());
    scheduler.start();
    return scheduler;
  }

  private JobDetail crearJob() {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("Link", this.link);
    dataMap.put("Organizaciones", this.organizaciones);

    return newJob(JobEnvioGuia.class)
        .withIdentity(UUID.randomUUID().toString(), "MiImpactoAmbientalApp")
        .usingJobData(dataMap)
        .build();
  }

  private Trigger crearTrigger() {
    return newTrigger()
        .withIdentity(UUID.randomUUID().toString(), "MiImpactoAmbientalApp")
        .startAt(Date.from(
            this.horaInicio
                .atDate(LocalDate.now())
                .toInstant(ZoneOffset.ofHours(-3))
        ))
        .withSchedule(
            simpleSchedule()
                .withIntervalInHours((int) this.intervalo.toHours())
                .repeatForever()
        )
        .build();
  }
}
