package ar.edu.utn.frba.dds.quemepongo.jobs;

import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import com.mchange.v2.log.slf4j.Slf4jMLog;

import java.util.Map;

public class GeneradorDeAlertas extends GeneradorBase {
  @Override
  public void run(Map<String, Object> context) {
    RepositorioAlertas repositorioAlertas = RepositorioAlertas.getInstance();
    RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstance();
    ServicioMeteorologico servicioMeteorologico = (ServicioMeteorologico) context.get("servicioMeteorologico");

    repositorioAlertas.update(servicioMeteorologico.getAlertas());
    repositorioUsuarios.getAll().forEach(it -> it.emitirAlertas(repositorioAlertas.getAll()));

    Slf4jMLog.info("ALERTAS GENERADAS CON ÉXITO");
  }
}
