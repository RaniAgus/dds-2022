package models;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class Administrador {
  String usuario;
  String contrasena;

  public Administrador(Validador validador, String usuario, String contrasena) {
    validador.validar(usuario, contrasena);
    this.contrasena = sha256Hex(contrasena);
    this.usuario = usuario;
    Administradores.getInstance().agregarAdministrador(this);
  }
}
