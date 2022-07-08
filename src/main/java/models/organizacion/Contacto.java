package models.organizacion;



import models.notificaciones.Notificador;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contacto {
  private String email;
  private String telefono;
  private List<Notificador> notificadores;

  public Contacto(String email, String telefono, List<Notificador> notificadores) {
    this.email = email;
    this.telefono = telefono;
    this.notificadores = notificadores;
  }

  public Contacto(String email, String telefono) {
    this.email = email;
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public void enviarGuia(String link) {
    notificadores
        .forEach(servicioDeNotificacion -> {
          servicioDeNotificacion.enviarGuiaRecomendacion(this, link);
        });
  }

}
