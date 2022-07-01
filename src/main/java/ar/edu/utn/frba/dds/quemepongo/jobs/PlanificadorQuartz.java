package ar.edu.utn.frba.dds.quemepongo.jobs;

import com.google.common.collect.ImmutableMap;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class PlanificadorQuartz {
  private Scheduler scheduler;

  public PlanificadorQuartz() throws SchedulerException {
    this.scheduler = new StdSchedulerFactory().getScheduler();
  }

  public PlanificadorQuartz agregarTarea(Tarea tarea, String cron) throws SchedulerException {
    scheduler.scheduleJob(crearJobDetail(tarea), crearTrigger(cron));
    return this;
  }

  public void iniciar() throws SchedulerException {
    scheduler.start();
  }

  private JobDetail crearJobDetail(Tarea tarea) {
    return newJob(JobQuartz.class)
        .withIdentity(tarea.getClass().getName(), "QueMePongoApp")
        .usingJobData(new JobDataMap(ImmutableMap.of("tarea", tarea)))
        .build();
  }

  private Trigger crearTrigger(String cron) {
    return newTrigger()
        .withIdentity(UUID.randomUUID().toString())
        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
        .build();
  }
}
