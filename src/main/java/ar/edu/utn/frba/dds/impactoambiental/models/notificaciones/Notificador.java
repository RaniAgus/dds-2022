package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

public interface Notificador {
  void enviarGuiaRecomendacion(Contacto contacto, String link);
}
