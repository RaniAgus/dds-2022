package models.organizacion.notificaciones;

import com.sun.org.apache.xpath.internal.operations.Or;
import models.organizacion.Contacto;
import models.organizacion.Organizacion;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;

public class jobEnvioGuia implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    String link = dataMap.getString("Link");
    List<Organizacion> listaDeOrganizaciones = (List<Organizacion>) dataMap.get("Organizaciones");
    listaDeOrganizaciones.forEach(unaOrganizacion -> {
        unaOrganizacion.enviarGuia(link);
    });

  }
}
