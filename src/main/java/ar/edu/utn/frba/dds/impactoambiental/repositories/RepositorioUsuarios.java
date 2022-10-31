package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;

public final class RepositorioUsuarios implements Repositorio<Usuario> {
  private static final RepositorioUsuarios instance = new RepositorioUsuarios();

  public static RepositorioUsuarios getInstance() {
    return instance;
  }

  private RepositorioUsuarios() {}

  public Either<Usuario> agregarUsuario(Usuario usuario) {
    if (existeUsuario(usuario.getUsuario())) {
      return Either.fallido("Nombre de usuario no disponible");
    }
    agregar(usuario);
    return Either.exitoso(usuario);
  }

  public Either<Usuario> obtenerUsuario(String usuario, String contrasena) {
    if (!existeUsuario(usuario)) {
      return Either.fallido("No existe el usuario: " + usuario);
    }
    return buscar("usuario", usuario, "contrasena", sha256Hex(contrasena))
        .map(Either::exitoso)
        .orElseGet(() -> Either.fallido("La contraseña no es válida"));
  }

  public boolean existeUsuario(String usuario) {
    return buscar("usuario", usuario).isPresent();
  }

  @Override
  public Class<Usuario> clase() {
    return Usuario.class;
  }
}
