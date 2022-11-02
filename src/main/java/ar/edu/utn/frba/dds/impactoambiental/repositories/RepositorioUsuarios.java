package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import java.util.Optional;

public final class RepositorioUsuarios implements Repositorio<Usuario> {
  private static final RepositorioUsuarios instance = new RepositorioUsuarios();

  public static RepositorioUsuarios getInstance() {
    return instance;
  }

  private RepositorioUsuarios() {}

  @Override
  public Optional<Usuario> agregar(Usuario usuario) {
    if (existeUsuario(usuario.getUsuario())) {
      return Optional.empty();
    }
    return Repositorio.super.agregar(usuario);
  }

  public Optional<Usuario> obtenerUsuario(String usuario, String contrasena) {
    return buscar("usuario", usuario, "contrasena", sha256Hex(contrasena));
  }

  public boolean existeUsuario(String usuario) {
    return buscar("usuario", usuario).isPresent();
  }

  @Override
  public Class<Usuario> clase() {
    return Usuario.class;
  }
}
