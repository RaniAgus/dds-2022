package ar.edu.utn.frba.dds.quemepongo.repository;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;

import java.util.HashSet;
import java.util.Set;

public class RepositorioAlertas {
  private static RepositorioAlertas INSTANCE = new RepositorioAlertas();
  private Set<Alerta> alertas = new HashSet<>();

  private RepositorioAlertas() {
  }

  public static RepositorioAlertas getInstance() {
    return INSTANCE;
  }

  public Set<Alerta> getAll() {
    return alertas;
  }

  public void update(Set<Alerta> alertas) {
    this.alertas.clear();
    this.alertas.addAll(alertas);
  }
}
