package ar.edu.utn.frba.dds.impactoambiental.models;

public class UsuarioDto {
  private String usuario;
  private String contrasena;

  public UsuarioDto(String usuario, String contrasena) {
    this.usuario = usuario;
    this.contrasena = contrasena;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }
}
