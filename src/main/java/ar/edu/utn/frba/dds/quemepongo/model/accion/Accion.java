package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import ar.edu.utn.frba.dds.quemepongo.model.guardarropas.Usuario;

public interface Accion {
  void emitirA(Usuario usuario, Clima clima);
}
