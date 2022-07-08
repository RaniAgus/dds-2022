package models;

import models.organizacion.Organizacion;
import models.notificaciones.SchedulerEnviarGuias;
import models.notificaciones.JobEnvioGuia;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.JobChainingJobListener;
import org.quartz.listeners.JobListenerSupport;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class SchedulerTest {

  @Test
  public void probarQueSeCalendarice() throws SchedulerException, InterruptedException {
    Organizacion organizacion = mock(Organizacion.class);
    SchedulerEnviarGuias schedulerEnviarGuias = new SchedulerEnviarGuias("0 * * ? * *", "linktes.com",  asList(organizacion));
    schedulerEnviarGuias.ejecutar();

    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler schede = sf.getScheduler();

    assertEquals(1,schede.getTriggersOfJob(new JobKey("job1","MiImpactoAmbientalApp")).size());
    assertEquals(schede.getTriggersOfJob(new JobKey("job1","MiImpactoAmbientalApp")).stream().findFirst().get().getFireTimeAfter(new Date()).toString(), new Date(System.currentTimeMillis()-(System.currentTimeMillis()%60000)+60000).toString());

    schede.deleteJob(new JobKey("job1","MiImpactoAmbientalApp"));
  }


}
