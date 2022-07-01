package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;

public class ServiceLocator {
  private RepositorioUsuarios repositorioUsuarios;
  private RepositorioAlertas repositorioAlertas;
  private ServicioMeteorologico servicioMeteorologico;

  public ServiceLocator(RepositorioUsuarios repositorioUsuarios,
                        RepositorioAlertas repositorioAlertas,
                        ServicioMeteorologico servicioMeteorologico) {
    this.repositorioUsuarios = repositorioUsuarios;
    this.repositorioAlertas = repositorioAlertas;
    this.servicioMeteorologico = servicioMeteorologico;
  }

  public RepositorioUsuarios getRepositorioUsuarios() {
    return repositorioUsuarios;
  }

  public RepositorioAlertas getRepositorioAlertas() {
    return repositorioAlertas;
  }

  public ServicioMeteorologico getServicioMeteorologico() {
    return servicioMeteorologico;
  }
}
