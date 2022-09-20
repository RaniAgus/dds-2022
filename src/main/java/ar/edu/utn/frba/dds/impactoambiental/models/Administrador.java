package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.validador.Validador;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
@Entity
public class Administrador {
  @Id
  @GeneratedValue
  private long id;
  private String usuario;
  private String contrasena;

  public Administrador(Validador validador, String usuario, String contrasena) {
    validador.validar(usuario, contrasena);
    this.contrasena = sha256Hex(contrasena);
    this.usuario = usuario;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }
}
