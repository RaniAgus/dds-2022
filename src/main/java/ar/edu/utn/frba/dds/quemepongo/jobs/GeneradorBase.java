package ar.edu.utn.frba.dds.quemepongo.jobs;

import org.quartz.*;
import java.util.Map;

public abstract class GeneradorBase implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    try {
      run(jobExecutionContext.getScheduler().getContext());
    } catch (SchedulerException e) {
      throw new JobExecutionException(e);
    }
  }

  protected abstract void run(Map<String, Object> context);
}
