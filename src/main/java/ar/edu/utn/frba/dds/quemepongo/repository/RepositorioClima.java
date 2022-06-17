package ar.edu.utn.frba.dds.quemepongo.repository;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioClima {
  private List<Clima> climaList = new ArrayList<>();

  public Clima getUltimaActualizacion() {
    return Iterables.getLast(climaList.stream().sorted().collect(Collectors.toList()));
  }

  public void agregarActualizacion(Clima clima) {
    climaList.add(clima);
  }
}
