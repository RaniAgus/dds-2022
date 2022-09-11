package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.SchedulerEnviarGuias;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.Collections;


public class MiImpactoAmbientalApp {
  public static void main(String[] args) throws SchedulerException {
    Scheduler scheduler = new SchedulerEnviarGuias(
        System.getenv("RECOMENDACIONES_CRON"),
        System.getenv("RECOMENDACIONES_URL"),
        Collections.emptyList()
    ).ejecutar();
  }
}
