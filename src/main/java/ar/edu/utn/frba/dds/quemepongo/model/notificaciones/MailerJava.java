package ar.edu.utn.frba.dds.quemepongo.model.notificaciones;

import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class MailerJava implements Mailer {
  private TemplateEngine templateEngine;
  private String from;
  private String password;

  public MailerJava(String from, String password, TemplateEngine templateEngine) {
    this.from = from;
    this.password = password;
    this.templateEngine = templateEngine;
  }

  public void enviarAlertas(String email, Set<Alerta> alertas) {
    enviarMensaje(
        email,
        "Nuevas alertas meteorológicas",
        templateEngine.obtenerHtmlAlertas(alertas),
        alertas.stream().map(it -> it + ".png").collect(Collectors.toList())
    );
  }

  private void enviarMensaje(String email, String subject, String mensaje, List<String> imagenes) {
    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.auth", "true");
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    Session session = Session.getInstance(props, obtenerAutenticador(from, password));
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(from));
      message.addRecipients(Message.RecipientType.BCC, obtenerDestinatario(email));
      message.setSubject(subject);
      message.setContent(obtenerContenido(mensaje, imagenes));
      Transport transport = session.getTransport("smtp");
      Transport.send(message);
      transport.close();
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  private Authenticator obtenerAutenticador(String username, String password) {
    return new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    };
  }

  private Address[] obtenerDestinatario(String email) throws AddressException {
    Address[] receptores = new Address[1];
    receptores[0] = new InternetAddress(email);
    return receptores;
  }

  private Multipart obtenerContenido(String mensaje, List<String> imagenes) throws MessagingException {
    MimeMultipart multipart = new MimeMultipart("related");
    BodyPart htmlPart = new MimeBodyPart();
    htmlPart.setContent(mensaje, "text/html");
    multipart.addBodyPart(htmlPart);
    for (String imagen : imagenes) {
      BodyPart imagePart = new MimeBodyPart();
      URLDataSource ds = new URLDataSource(
          getClass().getClassLoader().getResource("img/" + imagen)
      );
      imagePart.setDataHandler(new DataHandler(ds));
      imagePart.setHeader("Content-ID", "<" + imagen + ">");
      multipart.addBodyPart(imagePart);
    }
    return multipart;
  }
}
