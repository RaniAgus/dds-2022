package models.notificaciones;

import models.organizacion.Organizacion;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerEnviarGuias {
  private String cronExpression;
  private String link;
  private List<Organizacion> organizaciones;

  public SchedulerEnviarGuias(String cronExpression, String link, List<Organizacion> organizaciones) {
    this.cronExpression = cronExpression;
    this.link = link;
    this.organizaciones = organizaciones;
  }

  public Scheduler ejecutar() {
    try {
      Scheduler scheduler = new StdSchedulerFactory().getScheduler();
      scheduler.scheduleJob(crearJob(), crearTrigger());
      scheduler.start();
      return scheduler;
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }

  private JobDetail crearJob() {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("Link", this.link);
    dataMap.put("Organizaciones", this.organizaciones);

    return newJob(JobEnvioGuia.class)
        .withIdentity("job1", "MiImpactoAmbientalApp")
        .usingJobData(dataMap)
        .build();
  }

  private Trigger crearTrigger() {
    return newTrigger()
        .withIdentity("job1", "MiImpactoAmbientalApp")
        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
        .build();
  }
}
