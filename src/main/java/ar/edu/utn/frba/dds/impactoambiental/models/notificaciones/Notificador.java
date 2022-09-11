package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Contacto;

public interface Notificador {
  void enviarGuiaRecomendacion(Contacto contacto, String mensaje);
}
