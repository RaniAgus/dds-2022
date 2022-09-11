package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public final class Administradores {
  private static final Administradores instance = new Administradores();
  private final List<Administrador> admins;

  public static Administradores getInstance() {
    return instance;
  }

  private Administradores() {
    this.admins = new ArrayList<>();
  }

  public void agregarAdministrador(Administrador administrador) {
    admins.add(administrador);
  }

  public Administrador obtenerAdministrador(String usuario, String contrasena) {
    if (!existeAdministrador(usuario)) {
      throw new UsuarioNoDisponibleExeption("No existe el usuario: " +  usuario);
    }
    return this.admins.stream()
        .filter(admin -> admin.getUsuario().equals(usuario) && admin.getContrasena().equals(sha256Hex(contrasena)))
        .findFirst()
        .orElseThrow(() -> new UsuarioNoDisponibleExeption("No se pudo validar que sea ese administrador"));
  }

  public boolean existeAdministrador(String usuario) {
    return this.admins.stream().anyMatch(admin -> admin.getUsuario().equals(usuario));
  }

  public void limpiar() {
    this.admins.clear();
  }
}
