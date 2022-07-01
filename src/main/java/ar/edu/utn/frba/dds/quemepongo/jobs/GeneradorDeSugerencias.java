package ar.edu.utn.frba.dds.quemepongo.jobs;

import ar.edu.utn.frba.dds.quemepongo.ServiceLocator;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;
import com.mchange.v2.log.slf4j.Slf4jMLog;

public class GeneradorDeSugerencias extends GeneradorBase {
  @Override
  public void run(ServiceLocator serviceLocator) {
    serviceLocator.getRepositorioUsuarios().getAll().forEach(Usuario::generarSugerencia);
    Slf4jMLog.info("SUGERENCIAS GENERADAS CON ÉXITO");
  }
}
