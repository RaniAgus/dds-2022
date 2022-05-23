package ar.edu.utn.frba.dds.quemepongo.model;

import java.util.List;

public class Usuario {
  private List<Guardarropas> guardarropas;

  public Usuario(List<Guardarropas> guardarropas) {
    this.guardarropas = guardarropas;
  }

  public void agregar(Guardarropas guardarropas) {
    this.guardarropas.add(guardarropas);
  }
}
