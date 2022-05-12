package models;

public class Administrador {
  String Usuario;
  String Contrasena;

  public Administrador(Validador validador,String usuario, String contrasena) {
    validador.validar(usuario,contrasena);
    this.Contrasena = contrasena;
    this.Usuario = usuario;
    Administradores.getInstance().agregarAdministrador(this);
  }
}
