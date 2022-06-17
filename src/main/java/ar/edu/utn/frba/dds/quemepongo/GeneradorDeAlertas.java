package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.clima.AccuWeather;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioClima;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;

public final class GeneradorDeAlertas {
  private static ServicioMeteorologico servicioMeteorologico = new AccuWeather();
  private static RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  private static RepositorioClima repositorioClima = new RepositorioClima();

  public static void main(String[] args) {
    Clima clima = servicioMeteorologico.getClima();
    repositorioClima.agregarActualizacion(clima);
    repositorioUsuarios.emitirAlertas(clima);
  }
}
