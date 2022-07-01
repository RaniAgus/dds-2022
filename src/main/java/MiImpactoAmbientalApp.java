import models.organizacion.notificaciones.SchedulerEnviarGuias;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;

import static java.lang.Integer.parseInt;

public class MiImpactoAmbientalApp {
  public static void main(String[] args) throws SchedulerException {
    Scheduler scheduler = new SchedulerEnviarGuias(
        LocalTime.parse(System.getenv("RECOMENDACIONES_HORA")),
        Duration.ofDays(parseInt(System.getenv("RECOMENDACIONES_FRECUENCIA"))),
        System.getenv("RECOMENDACIONES_URL"),
        Collections.emptyList()
    ).ejecutar();
  }
}
