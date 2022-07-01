package ar.edu.utn.frba.dds.quemepongo.jobs;

import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import com.mchange.v2.log.slf4j.Slf4jMLog;

import java.time.LocalTime;


public class GenerarAlertas implements Tarea {
  private RepositorioAlertas repositorioAlertas;
  private RepositorioUsuarios repositorioUsuarios;
  private ServicioMeteorologico servicioMeteorologico;

  public GenerarAlertas(RepositorioAlertas repositorioAlertas,
                        RepositorioUsuarios repositorioUsuarios,
                        ServicioMeteorologico servicioMeteorologico) {
    this.repositorioAlertas = repositorioAlertas;
    this.repositorioUsuarios = repositorioUsuarios;
    this.servicioMeteorologico = servicioMeteorologico;
  }

  @Override
  public void ejecutar() {
    repositorioAlertas.update(servicioMeteorologico.getAlertas());
    repositorioUsuarios.getAll().forEach(it -> it.emitirAlertas(repositorioAlertas.getAll()));
    Slf4jMLog.info("ALERTAS GENERADAS CON ÉXITO A LAS " + LocalTime.now().toString());
  }
}
