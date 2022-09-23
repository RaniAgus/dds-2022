package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class NotificadorPorMail implements Notificador {
  public static final NotificadorPorMail INSTANCE = new NotificadorPorMail(
      System.getenv("SMTP_USER"),
      System.getenv("SMTP_PASSWORD")
  );

  private final String smtpUser;
  private final String smtpPassword;

  private NotificadorPorMail(String smtpUser, String smtpPassword) {
    this.smtpUser = smtpUser;
    this.smtpPassword = smtpPassword;
  }

  @Override
  public void enviarGuiaRecomendacion(Contacto contacto, String link) {
    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.auth", "true");
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    Session session = Session.getInstance(props, this.getPasswordAuthentication(smtpUser, smtpPassword));
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(smtpUser));
      message.addRecipient(Message.RecipientType.BCC, new InternetAddress(contacto.getEmail()));
      message.setSubject("Guía Recomendaciones");
      message.setText(link);

      Transport transport = session.getTransport("smtp");
      Transport.send(message);
      transport.close();
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  private Authenticator getPasswordAuthentication(String username, String password) {
    return new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    };
  }

  private Address[] obtenerDestinatarios(List<Contacto> contactos) {
    Address[] receptores = new Address[contactos.size()];
    return contactos.stream()
        .map(this::obtenerDireccion)
        .collect(Collectors.toList())
        .toArray(receptores);
  }

  private InternetAddress obtenerDireccion(Contacto contacto) {
    try {
      return new InternetAddress(contacto.getEmail());
    } catch (AddressException e) {
      throw new RuntimeException(e);
    }
  }
}
