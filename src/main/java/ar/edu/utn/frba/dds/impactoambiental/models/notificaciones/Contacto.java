package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Entity
public class Contacto extends EntidadPersistente {
  private String email;
  private String telefono;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<MedioDeNotificacion> mediosDeNotificacion;

  public Contacto() {
  }

  public Contacto(String email, String telefono, List<MedioDeNotificacion> mediosDeNotificacion) {
    this.email = email;
    this.telefono = telefono;
    this.mediosDeNotificacion = mediosDeNotificacion;
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
    mediosDeNotificacion
        .forEach(servicioDeNotificacion -> {
          servicioDeNotificacion.enviarGuiaRecomendacion(this, link);
        });
  }

}
