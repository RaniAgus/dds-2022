package models;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import models.validador.Validador;

public class Administrador {
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
