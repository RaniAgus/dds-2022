package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.clima.AccuWeather;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Temperatura;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;

public final class GeneradorDeSugerencias {
  private static ServicioMeteorologico servicioMeteorologico = new AccuWeather();
  private static RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();

  public static void main(String[] args) {
    Temperatura temperatura = servicioMeteorologico.getTemperatura();
    repositorioUsuarios.generarSugerencias(temperatura);
  }
}
