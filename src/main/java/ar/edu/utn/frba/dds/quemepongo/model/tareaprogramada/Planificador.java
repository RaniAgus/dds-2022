package ar.edu.utn.frba.dds.quemepongo.model.tareaprogramada;

import com.google.common.collect.ImmutableMap;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class Planificador {
  private Scheduler scheduler;

  public Planificador() throws SchedulerException {
    this.scheduler = new StdSchedulerFactory().getScheduler();
  }

  public Planificador agregarTarea(TareaProgramada tareaProgramada, String cron) throws SchedulerException {
    scheduler.scheduleJob(crearJobDetail(tareaProgramada), crearTrigger(cron));
    return this;
  }

  public void iniciar() throws SchedulerException {
    scheduler.start();
  }

  private Trigger crearTrigger(String cron) {
    return newTrigger()
        .withIdentity(UUID.randomUUID().toString())
        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
        .build();
  }

  private JobDetail crearJobDetail(TareaProgramada tareaProgramada) {
    return newJob(TareaProgramadaQuartz.class)
        .withIdentity(tareaProgramada.getClass().getName(), "QueMePongoApp")
        .usingJobData(new JobDataMap(ImmutableMap.of("tarea", tareaProgramada)))
        .build();
  }

  public static class TareaProgramadaQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
      ((TareaProgramada) jobExecutionContext.getJobDetail().getJobDataMap().get("tarea")).ejecutar();
    }
  }
}
