package models.organizacion.notificaciones;

import models.organizacion.Contacto;

import javax.mail.MessagingException;
import java.util.List;

public interface ServicioDeNotificacion {
  void enviarGuiaRecomendacion(List<Contacto> contactos, String mensaje) throws MessagingException;
}
