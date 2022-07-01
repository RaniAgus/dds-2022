package ar.edu.utn.frba.dds.quemepongo.model.tareaprogramada;

import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import com.mchange.v2.log.slf4j.Slf4jMLog;

import java.time.LocalTime;

public class GenerarSugerencias implements TareaProgramada {
  private RepositorioUsuarios repositorioUsuarios;

  public GenerarSugerencias(RepositorioUsuarios repositorioUsuarios) {
    this.repositorioUsuarios = repositorioUsuarios;
  }

  @Override
  public void ejecutar() {
    repositorioUsuarios.getAll().forEach(Usuario::generarSugerencia);
    Slf4jMLog.info("SUGERENCIAS GENERADAS CON ÉXITO A LAS " + LocalTime.now().toString());
  }
}
