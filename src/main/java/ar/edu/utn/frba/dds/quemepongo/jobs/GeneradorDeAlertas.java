package ar.edu.utn.frba.dds.quemepongo.jobs;

import ar.edu.utn.frba.dds.quemepongo.ServiceLocator;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import com.mchange.v2.log.slf4j.Slf4jMLog;

public class GeneradorDeAlertas extends GeneradorBase {
  @Override
  public void run(ServiceLocator serviceLocator) {
    RepositorioAlertas repositorioAlertas = serviceLocator.getRepositorioAlertas();
    RepositorioUsuarios repositorioUsuarios = serviceLocator.getRepositorioUsuarios();
    ServicioMeteorologico servicioMeteorologico = serviceLocator.getServicioMeteorologico();

    repositorioAlertas.update(servicioMeteorologico.getAlertas());
    repositorioUsuarios.getAll().forEach(it -> it.emitirAlertas(repositorioAlertas.getAll()));

    Slf4jMLog.info("ALERTAS GENERADAS CON ÉXITO");
  }
}
