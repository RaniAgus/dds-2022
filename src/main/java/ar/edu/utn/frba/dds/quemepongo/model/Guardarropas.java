package ar.edu.utn.frba.dds.quemepongo.model;

import java.util.List;
import java.util.stream.Collectors;

public class Guardarropas {
  private List<Prenda> prendas;

  public Guardarropas(List<Prenda> prendas) {
    this.prendas = prendas;
  }

  public List<Prenda> getPrendasSugeribles(Categoria categoria) {
    return prendas.stream()
        .filter(it -> it.esDeCategoria(categoria) && it.esSugerible())
        .collect(Collectors.toList());
  }
}
