package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario extends EntidadPersistente {
  private String usuario;
  private String contrasena;

  protected Usuario() {}

  public Usuario(String usuario, String contrasena) {
    this.usuario = usuario;
    this.contrasena = sha256Hex(contrasena);
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public abstract String getHomeUrl();
}
