package ar.edu.utn.frba.dds.quemepongo.model.tareaprogramada;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import com.mchange.v2.log.slf4j.Slf4jMLog;

import java.time.LocalTime;
import java.util.Set;

public class GenerarAlertas implements TareaProgramada {
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
    Set<Alerta> nuevasAlertas = repositorioAlertas.actualizar(servicioMeteorologico.getAlertas());
    repositorioUsuarios.getAll().forEach(it -> it.emitirAlertas(nuevasAlertas));
    Slf4jMLog.info("Alertas generadas con éxito a las: " + LocalTime.now().toString());
  }
}
