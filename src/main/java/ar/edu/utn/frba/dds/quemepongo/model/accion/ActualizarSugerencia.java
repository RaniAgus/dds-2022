package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.Set;

public class ActualizarSugerencia implements Accion {
  @Override
  public void emitirA(Usuario usuario, Set<Alerta> alertas) {
    usuario.generarSugerencia();
  }
}
