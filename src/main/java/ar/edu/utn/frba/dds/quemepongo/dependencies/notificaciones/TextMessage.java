package ar.edu.utn.frba.dds.quemepongo.dependencies.notificaciones;

import java.util.stream.Stream;

public class TextMessage {
  private String messaging_product = "whatsapp";
  private String recipient_type = "individual";
  private String to;
  private String type = "text";
  private Text text = new Text();

  public TextMessage(String phoneNumber, String message) {
    this.to = phoneNumber;
    this.text.preview_url = Stream.of("http://", "https://").anyMatch(message::contains);
    this.text.body = message;
  }

  public static class Text {
    protected boolean preview_url;
    protected String body;
  }
}
