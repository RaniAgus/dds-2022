package models.organizacion.notificaciones;

import models.organizacion.Organizacion;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerEnviarGuias {
  public Integer intervaloDeDiasEnHoras;
  public String link;
  public List<Organizacion> organizaciones;

  public SchedulerEnviarGuias(Integer intervaloDeDias, String link, List<Organizacion> organizaciones) {
    this.intervaloDeDiasEnHoras = intervaloDeDias * 24;
    this.link = link;
    this.organizaciones = organizaciones;
  }

  public void executeCronJob() throws SchedulerException, InterruptedException {
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler sched = sf.getScheduler();
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("Organizaciones",this.organizaciones);
    dataMap.put("Link",this.link);

    JobDetail job = newJob(jobEnvioGuia.class)
        .withIdentity("job1", "group1")
        .usingJobData(dataMap)
        .build();

    Trigger trigger = newTrigger()
        .withIdentity("trigger3", "group1")
        .withSchedule(simpleSchedule().withIntervalInHours(this.intervaloDeDiasEnHoras).repeatForever())
        .forJob("job1", "group1")
        .build();

    sched.scheduleJob(job, trigger);
    sched.start();

    Thread.sleep(60L * 600000L * (this.intervaloDeDiasEnHoras-1));
    //PREGUNTAR!!!!!
  }
}
