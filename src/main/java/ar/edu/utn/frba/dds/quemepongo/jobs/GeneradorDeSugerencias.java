package ar.edu.utn.frba.dds.quemepongo.jobs;

import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import com.mchange.v2.log.slf4j.Slf4jMLog;

import java.util.Map;

public class GeneradorDeSugerencias extends GeneradorBase {
  @Override
  public void run(Map<String, Object> context) {
    RepositorioUsuarios.getInstance().getAll().forEach(Usuario::generarSugerencia);
    Slf4jMLog.info("SUGERENCIAS GENERADAS CON ÉXITO");
  }
}
