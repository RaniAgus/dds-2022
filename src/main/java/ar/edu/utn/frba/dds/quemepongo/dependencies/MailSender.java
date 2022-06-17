package ar.edu.utn.frba.dds.quemepongo.dependencies;

public interface MailSender {
  void send(String address, String message);
}
