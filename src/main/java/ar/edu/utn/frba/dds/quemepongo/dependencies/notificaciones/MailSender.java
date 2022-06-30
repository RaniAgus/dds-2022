package ar.edu.utn.frba.dds.quemepongo.dependencies.notificaciones;

public interface MailSender {
  void send(String address, String message);
}
