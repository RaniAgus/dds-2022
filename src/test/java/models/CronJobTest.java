package models;

import models.organizacion.Organizacion;
import models.notificaciones.JobEnvioGuia;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.quartz.JobBuilder.newJob;

public class CronJobTest {

  @Test
  public void verificarQueTodasLasOrgNotifiquen() throws JobExecutionException {

    Organizacion unaOrganizacion = mock(Organizacion.class);
    Organizacion otraOrganizacion = mock(Organizacion.class);

    JobDataMap dataMap = new JobDataMap();
    dataMap.put("Organizaciones",asList(unaOrganizacion,otraOrganizacion));
    dataMap.put("Link","linktest.com");

    JobDetail job = newJob(JobEnvioGuia.class)
        .withIdentity("job1", "group1")
        .usingJobData(dataMap)
        .build();

    JobExecutionContext jobExecutionContext= mock(JobExecutionContext.class);
    when(jobExecutionContext.getJobDetail()).thenReturn(job);

    JobEnvioGuia jobEnvioGuia= new JobEnvioGuia();
    jobEnvioGuia.execute(jobExecutionContext);

    Mockito.verify(unaOrganizacion).enviarGuia("linktest.com");
    Mockito.verify(otraOrganizacion).enviarGuia("linktest.com");
  }
}
