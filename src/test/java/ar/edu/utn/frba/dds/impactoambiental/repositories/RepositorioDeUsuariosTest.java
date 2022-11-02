package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.utn.frba.dds.impactoambiental.models.BaseTest;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioDeUsuariosTest extends BaseTest implements PersistenceTest {
  private Organizacion organizacion;

  @BeforeEach
  public void crearOrganizacion() {
    organizacion = crearOrganizacionVacia();
    RepositorioOrganizaciones.getInstance().agregar(organizacion);
  }

  @AfterEach
  public void limpiarUsuarios() {
    RepositorioUsuarios.getInstance().limpiar();
    RepositorioOrganizaciones.getInstance().limpiar();
  }

  @Test
  public void sePuedeObtenerUnUsuarioIngresandoLaContraseniaCorrecta() {
    Usuario usuario = crearUsuario(organizacion);

    RepositorioUsuarios.getInstance().agregarUsuario(usuario);

    Either<Usuario> resultado = RepositorioUsuarios.getInstance().obtenerUsuario("Juancito", "ContraSUper*MegaS3gUr4");

    assertThat(resultado.getValor()).isEqualTo(usuario);
  }

  @Test
  public void noSePuedeObtenerUnUsuarioIngresandoLaContraseniaIncorrecta() {
    Usuario usuario = crearUsuario(organizacion);
    RepositorioUsuarios.getInstance().agregarUsuario(usuario);

    Either<Usuario> resultado = RepositorioUsuarios.getInstance().obtenerUsuario("Juancito","contraIncorrecta");

    assertThat(resultado.getErrores()).containsExactlyInAnyOrder("La contraseña no es válida");
  }

  @Test
  public void noSePuedeObtenerUnUsuarioIngresandoUnNombreInexistente() {
    Either<Usuario> resultado = RepositorioUsuarios.getInstance().obtenerUsuario("Usuario_Inexistente","contraIncorrecta");

    assertThat(resultado.getErrores()).containsExactlyInAnyOrder("No existe el usuario: Usuario_Inexistente");
  }

}
