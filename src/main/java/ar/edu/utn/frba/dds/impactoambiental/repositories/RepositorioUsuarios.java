package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;
import ar.edu.utn.frba.dds.impactoambiental.models.Usuario;

public final class RepositorioUsuarios implements Repositorio<Usuario> {
  private static final RepositorioUsuarios instance = new RepositorioUsuarios();

  public static RepositorioUsuarios getInstance() {
    return instance;
  }

  private RepositorioUsuarios() {}

  public void agregarAdministrador(Usuario usuario) {
    if (existeAdministrador(usuario.getUsuario())) {
      throw new UsuarioNoDisponibleExeption("Nombre de usuario no disponible");
    }
    agregar(usuario);
  }

  public Usuario obtenerAdministrador(String usuario, String contrasena) {
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
  public Class<Usuario> clase() {
    return Usuario.class;
  }
}
