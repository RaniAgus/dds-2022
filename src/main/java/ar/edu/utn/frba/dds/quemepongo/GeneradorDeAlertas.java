package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.model.clima.AccuWeather;

import static ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas.alertas;
import static ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios.usuarios;

public final class GeneradorDeAlertas {
  public static void main(String[] args) {
    alertas().update(new AccuWeather().getAlertas());
    usuarios().getAll().forEach(it -> it.emitirAlertas(alertas().getAll()));
  }
}
