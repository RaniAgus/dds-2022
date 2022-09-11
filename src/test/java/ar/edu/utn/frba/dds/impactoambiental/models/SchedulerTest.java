package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.SchedulerEnviarGuias;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

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

    assertEquals(1,schede.getTriggersOfJob(new JobKey("job1","ar.edu.utn.frba.dds.impactoambiental.MiImpactoAmbientalApp")).size());
    assertEquals(schede.getTriggersOfJob(new JobKey("job1","ar.edu.utn.frba.dds.impactoambiental.MiImpactoAmbientalApp")).stream().findFirst().get().getFireTimeAfter(new Date()).toString(), new Date(System.currentTimeMillis()-(System.currentTimeMillis()%60000)+60000).toString());

    schede.deleteJob(new JobKey("job1","ar.edu.utn.frba.dds.impactoambiental.MiImpactoAmbientalApp"));
  }


}
