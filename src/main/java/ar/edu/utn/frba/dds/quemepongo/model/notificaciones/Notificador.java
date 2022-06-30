package ar.edu.utn.frba.dds.quemepongo.model.notificaciones;

import ar.edu.utn.frba.dds.quemepongo.model.usuario.Usuario;

public interface Notificador {
  void enviarMensaje(Usuario usuario, String mensaje);
}
