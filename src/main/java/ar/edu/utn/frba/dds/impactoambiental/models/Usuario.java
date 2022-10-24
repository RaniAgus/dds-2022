package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.utils.Chequeador;
import javax.persistence.Entity;

@Entity
public class Usuario extends EntidadPersistente {
  private String usuario;
  private String contrasena;

  protected Usuario() {}

  public Usuario(Chequeador<UsuarioDto> validador, UsuarioDto usuarioDto) {
    validador.validar(usuarioDto);
    this.contrasena = sha256Hex(usuarioDto.getContrasena());
    this.usuario = usuarioDto.getUsuario();
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasena() {
    return contrasena;
  }
}
