package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class JobEnvioGuia {
  private final List<Organizacion> organizaciones;
  private final String link;

  public static void main(String[] args) {
    new JobEnvioGuia(Collections.emptyList(),
        System.getenv("RECOMENDACIONES_URL")).ejecutar();
  }

  public JobEnvioGuia(List<Organizacion> organizaciones, String link) {
    this.organizaciones = organizaciones;
    this.link = link;
  }

  public void ejecutar() {
    organizaciones.forEach(it -> it.enviarGuia(link));
    System.out.println("Recomendaciones enviadas a las " + LocalDateTime.now());
  }

}
