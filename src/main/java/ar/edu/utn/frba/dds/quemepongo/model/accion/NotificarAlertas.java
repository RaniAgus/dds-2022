package ar.edu.utn.frba.dds.quemepongo.model.accion;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.notificaciones.Notificador;
import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

import java.util.Map;
import java.util.Set;

public class NotificarAlertas implements AccionTrasAlertas {
  private Notificador notificador;
  private Map<Alerta, String> mensajes;

  public NotificarAlertas(Notificador notificador, Map<Alerta, String> mensajes) {
    this.notificador = notificador;
    this.mensajes = mensajes;
  }

  @Override
  public void anteNuevasAlertas(Usuario usuario, Set<Alerta> alertas) {
    alertas.forEach(it -> notificador.enviarMensaje(usuario, mensajes.get(it)));
  }
}
