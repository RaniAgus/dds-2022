package models.organizacion.notificaciones;

import models.organizacion.Contacto;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailDelivered  implements ServicioDeNotificacion {
  private  static final MailDelivered INSTANCE = new MailDelivered();

  public static MailDelivered instance() {
    return INSTANCE;
  }

  private MailDelivered() {
  }

  protected Authenticator getPasswordAuthentication(String username, String password) {
    return new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    };

  }

  @Override
  public void enviarGuiaRecomendacion(List<Contacto> contactos, String mensaje)
      throws MessagingException {
    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.auth", "true");
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    String remitente = System.getenv("MAIL_SENDER_REMITENTE");
    String clave = System.getenv("MAIL_SENDER_CLAVE");

    Session session = Session.getInstance(props,
        this.getPasswordAuthentication(remitente, clave));


    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(remitente));
    message.addRecipients(Message.RecipientType.BCC, this.obtenerDestinatarios(contactos));
    message.setSubject("Guía Recomendaciones");
    message.setText(mensaje);

    Transport transport = session.getTransport("smtp");
    Transport.send(message);
    transport.close();
  }

  public Address[] obtenerDestinatarios(List<Contacto> contactos) {
    Address[] receptores = new Address[contactos.size()];
    return contactos
          .stream()
          .map(contacto -> {
            InternetAddress address = new InternetAddress();
            try {
              address = new InternetAddress(contacto.getEmail());
            } catch (AddressException ignored) { }
            return address;
          })
          .collect(Collectors.toList()).toArray(receptores);

  }
}
