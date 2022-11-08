package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioOrganizaciones;
import java.time.LocalDateTime;
import java.util.List;

public class JobEnvioGuia {
  private final List<Organizacion> organizaciones;
  private final String link;

  public static void main(String[] args) {
    new JobEnvioGuia(RepositorioOrganizaciones.getInstance().obtenerTodos(),
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
