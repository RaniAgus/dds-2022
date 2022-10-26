package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;
import ar.edu.utn.frba.dds.impactoambiental.models.Administrador;

public final class RepositorioDeAdministradores implements Repositorio<Administrador> {
  private static final RepositorioDeAdministradores instance = new RepositorioDeAdministradores();

  public static RepositorioDeAdministradores getInstance() {
    return instance;
  }

  private RepositorioDeAdministradores() {}

  public void agregarAdministrador(Administrador administrador) {
    if (existeAdministrador(administrador.getUsuario())) {
      throw new UsuarioNoDisponibleExeption("Nombre de usuario no disponible");
    }
    agregar(administrador);
  }

  public Administrador obtenerAdministrador(String usuario, String contrasena) {
    if (!existeAdministrador(usuario)) {
      throw new UsuarioNoDisponibleExeption("No existe el usuario: " + usuario);
    }
    return buscar("usuario", usuario, "contrasena", sha256Hex(contrasena))
        .orElseThrow(() ->
            new UsuarioNoDisponibleExeption("No se pudo validar que sea ese administrador"));
  }

  public boolean existeAdministrador(String usuario) {
    return buscar("usuario", usuario).isPresent();
  }

  @Override
  public Class<Administrador> clase() {
    return Administrador.class;
  }
}
