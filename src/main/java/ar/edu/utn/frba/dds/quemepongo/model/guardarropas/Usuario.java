package ar.edu.utn.frba.dds.quemepongo.model.guardarropas;

import java.util.List;

public class Usuario {
  private List<Guardarropa> guardarropas;

  public Usuario(List<Guardarropa> guardarropas) {
    this.guardarropas = guardarropas;
  }

  public void agregar(Guardarropa guardarropa) {
    guardarropas.add(guardarropa);
  }
}
