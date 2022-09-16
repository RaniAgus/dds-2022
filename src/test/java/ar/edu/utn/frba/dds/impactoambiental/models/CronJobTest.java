package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.JobEnvioGuia;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;

public class CronJobTest {
  @Test
  public void verificarQueTodasLasOrgNotifiquen() {
    Organizacion unaOrganizacion = mock(Organizacion.class);
    Organizacion otraOrganizacion = mock(Organizacion.class);

    JobEnvioGuia jobEnvioGuia = new JobEnvioGuia(asList(unaOrganizacion, otraOrganizacion), "linktest.com");
    jobEnvioGuia.ejecutar();

    Mockito.verify(unaOrganizacion).enviarGuia("linktest.com");
    Mockito.verify(otraOrganizacion).enviarGuia("linktest.com");
  }
}
