package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.Set;

public class ActualizarSugerencia implements AccionTrasAlertas {
  @Override
  public void anteNuevasAlertas(Usuario usuario, Set<Alerta> alertas) {
    usuario.generarSugerencia();
  }
}
