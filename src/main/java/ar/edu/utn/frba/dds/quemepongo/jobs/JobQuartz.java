package ar.edu.utn.frba.dds.quemepongo.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class JobQuartz implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    ((Tarea) jobExecutionContext.getJobDetail().getJobDataMap().get("tarea")).ejecutar();
  }
}
