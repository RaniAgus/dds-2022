package ar.edu.utn.frba.dds.quemepongo.jobs;

import ar.edu.utn.frba.dds.quemepongo.ServiceLocator;
import org.quartz.*;

public abstract class GeneradorBase implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    try {
      run((ServiceLocator) jobExecutionContext.getScheduler().getContext().get("serviceLocator"));
    } catch (SchedulerException e) {
      throw new JobExecutionException(e);
    }
  }

  protected abstract void run(ServiceLocator serviceLocator);
}
