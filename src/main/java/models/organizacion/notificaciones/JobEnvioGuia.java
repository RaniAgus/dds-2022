package models.organizacion.notificaciones;

import com.mchange.v2.log.slf4j.Slf4jMLog;
import models.organizacion.Organizacion;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class JobEnvioGuia implements Job {
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    String link = dataMap.getString("Link");

    List<Organizacion> listaDeOrganizaciones = (List<Organizacion>) dataMap.get("Organizaciones");
    listaDeOrganizaciones.forEach(it -> it.enviarGuia(link));

    Slf4jMLog.info("Recomendaciones enviadas");
  }
}
