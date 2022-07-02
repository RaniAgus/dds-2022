package ar.edu.utn.frba.dds.quemepongo.repository;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class RepositorioAlertas {
  private Set<Alerta> alertas = new HashSet<>();

  public Set<Alerta> getAll() {
    return alertas;
  }

  public Set<Alerta> actualizar(Set<Alerta> nuevasAlertas) {
    Set<Alerta> anterioresAlertas = getAll();
    this.alertas.clear();
    this.alertas.addAll(nuevasAlertas);
    return Sets.difference(nuevasAlertas, anterioresAlertas);
  }
}
