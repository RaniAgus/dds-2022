package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;
import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionFallidaException;
import ar.edu.utn.frba.dds.impactoambiental.models.BaseTest;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioDeUsuariosTest extends BaseTest implements PersistenceTest {

  @BeforeEach
  public void limpiarAdministradores() {
    RepositorioUsuarios.getInstance().limpiar();
  }

  @Test
  public void alCrearUnUsuarioSeValidanContraseniasDebiles()  {
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.singletonList("admin1234"));

    assertThatThrownBy(() -> crearUsuario("admin1234"))
        .isExactlyInstanceOf(ValidacionFallidaException.class)
        .hasMessageContainingAll(
            "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.",
            "La contraseña no debe tener 4 caracteres consecutivos."
        );
  }


  @Test
  public void sePuedeObtenerUnUsuarioIngresandoLaContraseniaCorrecta() throws Throwable {
    Usuario usuario = crearUsuario("ContraSUper*MegaS3gUr4");

    RepositorioUsuarios.getInstance().agregarAdministrador(usuario);

    assertThat(RepositorioUsuarios.getInstance().obtenerAdministrador("Juancito", "ContraSUper*MegaS3gUr4"))
        .isEqualTo(usuario);
  }

  @Test
  public void noSePuedeObtenerUnUsuarioIngresandoLaContraseniaIncorrecta() {
    Usuario usuario = crearUsuario("ContraSUper*MegaS3gUr4");
    RepositorioUsuarios.getInstance().agregarAdministrador(usuario);

    assertThatThrownBy(() -> RepositorioUsuarios.getInstance().obtenerAdministrador("Juancito","contraIncorrecta"))
        .isExactlyInstanceOf(UsuarioNoDisponibleExeption.class)
        .hasMessage("No se pudo validar que sea ese administrador");
  }

  @Test
  public void noSePuedeObtenerUnUsuarioIngresandoUnNombreInexistente() {
    assertThatThrownBy(() -> RepositorioUsuarios.getInstance().obtenerAdministrador("Usuario_Inexistente","contraIncorrecta"))
        .isExactlyInstanceOf(UsuarioNoDisponibleExeption.class)
        .hasMessage("No existe el usuario: Usuario_Inexistente");
  }

}
