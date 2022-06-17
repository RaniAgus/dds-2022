package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Clima;
import ar.edu.utn.frba.dds.quemepongo.model.guardarropas.Usuario;

public class ActualizarSugerencia implements Accion {
  @Override
  public void emitirA(Usuario usuario, Clima clima) {
    usuario.generarSugerencia(clima.getTemperatura());
  }
}
