package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;



public final class Administradores {
  private static final Administradores instance = new Administradores();
  private final List<Administrador> admins;
  private EntityManager entityManager;

  public static Administradores getInstance() {
    return instance;
  }

  private Administradores() {
    this.admins = new ArrayList<>();
  }

  public void agregarAdministrador(Administrador administrador) {
    if (existeAdministrador(administrador.getUsuario())) {
      throw new UsuarioNoDisponibleExeption("Nombre de usuario no disponible");
    }
    entityManager.persist(administrador);
  }

  public Administrador obtenerAdministrador(String usuario, String contrasena) throws Throwable {
    if (!existeAdministrador(usuario)) {
      throw new UsuarioNoDisponibleExeption("No existe el usuario: " +  usuario);
    }

    return (Administrador) this.entityManager.createQuery("SELECT administrador from Administrador"
            + " where administrador.usuario = ?1 " + "and administrador.contraseña = ? 2")
        .setParameter("1", usuario)
        .setParameter("2", sha256Hex(contrasena))
        .getResultList().stream()
        .findFirst()
        .orElseThrow(() ->
            new UsuarioNoDisponibleExeption("No se pudo validar que sea ese administrador"));
  }

  public boolean existeAdministrador(String usuario) {
    return !entityManager
        .createQuery("SELECT administrador from Administrador where administrador.usuario = ?1")
        .setParameter("1", usuario).getResultList().isEmpty();
  }

  public void limpiar() {
    this.admins.clear();
  }
}
