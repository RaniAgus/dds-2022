package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ContrasenaDebilException;
import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class AdministradorTest extends BaseTest {

  @BeforeEach
  public void limpiarAdministradores() {
    RepositorioDeAdministradores.getInstance().limpiar();
  }

  @Test
  public void alCrearUnUsuarioSeValidanContraseniasDebiles()  {
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.singletonList("admin1234"));

    assertThatThrownBy(() -> crearAdministrador("admin1234"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessageContainingAll(
            "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.",
            "La contraseña no debe tener 4 caracteres consecutivos."
        );
  }


  @Test
  public void sePuedeObtenerUnUsuarioIngresandoLaContraseniaCorrecta() throws Throwable {
    Administrador administrador = crearAdministrador("ContraSUper*MegaS3gUr4");

    RepositorioDeAdministradores.getInstance().agregarAdministrador(administrador);

    assertThat(RepositorioDeAdministradores.getInstance().obtenerAdministrador("Juancito", "ContraSUper*MegaS3gUr4"))
        .isEqualTo(administrador);
  }

  @Test
  public void noSePuedeObtenerUnUsuarioIngresandoLaContraseniaIncorrecta() {
    Administrador administrador = crearAdministrador("ContraSUper*MegaS3gUr4");
    RepositorioDeAdministradores.getInstance().agregarAdministrador(administrador);

    assertThatThrownBy(() -> RepositorioDeAdministradores.getInstance().obtenerAdministrador("Juancito","contraIncorrecta"))
        .isExactlyInstanceOf(UsuarioNoDisponibleExeption.class)
        .hasMessage("No se pudo validar que sea ese administrador");
  }

  @Test
  public void noSePuedeObtenerUnUsuarioIngresandoUnNombreInexistente() {
    assertThatThrownBy(() -> RepositorioDeAdministradores.getInstance().obtenerAdministrador("Usuario_Inexistente","contraIncorrecta"))
        .isExactlyInstanceOf(UsuarioNoDisponibleExeption.class)
        .hasMessage("No existe el usuario: Usuario_Inexistente");
  }

}
