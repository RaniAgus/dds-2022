package models.organizacion.notificaciones;

import models.organizacion.Contacto;

import java.util.List;

public interface Notificador {
  void enviarGuiaRecomendacion(List<Contacto> contactos, String mensaje);
}
