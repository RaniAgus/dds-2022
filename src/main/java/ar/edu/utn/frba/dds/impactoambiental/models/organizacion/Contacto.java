package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;



import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
@Entity
public class Contacto {
  @Id
  @GeneratedValue
  private long id;
  private String email;
  private String telefono;
  @Embedded
  private List<MedioDeNotificacion> mediosDeNotificacion;

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
