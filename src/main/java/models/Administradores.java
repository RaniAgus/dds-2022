package models;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.exceptions.UsuarioNoDisponibleExeption;


public final class Administradores {
  private static final Administradores instance = new Administradores();
  public List<Administrador> admins;

  public static Administradores getInstance() {
    return instance;
  }

  private Administradores() {
    this.admins = new ArrayList<Administrador>();
  }

  public void agregarAdministrador(Administrador administrador) {
    admins.add(administrador);
  }

  public Administrador obtenerAdministrador(String usuario, String contrasena) {
    if (!existeAdministrador(usuario)) {
      throw new UsuarioNoDisponibleExeption("No existe el usuario: " +  usuario);
    }
    Optional<Administrador> administrador = this.admins
        .stream()
        .filter(admin -> admin.getUsuario().equals(usuario)
        && admin.getContrasena().equals(sha256Hex(contrasena))).findFirst();
    return administrador.orElseThrow(() -> new UsuarioNoDisponibleExeption(
        "No se pudo validar que sea ese administrador"));
  }

  public boolean existeAdministrador(String usuario) {
    return this.admins.stream().anyMatch(admin -> admin.getUsuario().equals(usuario));
  }
}
