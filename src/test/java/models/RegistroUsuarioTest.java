package models;

import models.exceptions.ContrasenaDebilException;
import models.exceptions.UsuarioNoDisponibleExeption;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegistroUsuarioTest {

  @Test
  public void validarContrasenasDebiles() {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();
    SoftAssertions soft = new SoftAssertions();

    soft.assertThatThrownBy(() -> registroUsuarios.verificarContrasenaDebil("corta"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("La contraseña debe tener al menos 8 caracteres");
    soft.assertThatThrownBy(() -> registroUsuarios.verificarContrasenaDebil("1234567890"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("La contraseña no debe tener 4 caracteres consecutivos");
    soft.assertThatThrownBy(() -> registroUsuarios.verificarContrasenaDebil("edcbajuhy"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("La contraseña no debe tener 4 caracteres consecutivos");
    soft.assertThatThrownBy(() -> registroUsuarios.verificarContrasenaDebil("holaaatodooos"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("La contraseña no debe repetir 3 veces seguidas un caracter. Secuencia encontrada: aaa");
    soft.assertThatThrownBy(() -> registroUsuarios.verificarContrasenaDebil("iloveyou"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("Contraseña dentro de las 10000 mas usadas. Elija otra por favor");
    soft.assertAll();
  }
  @Test
  public void validarContrasenaUsarioPorDefecto() {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();

    assertThatThrownBy(() -> registroUsuarios.guardarUsuario("usuario", "usuario"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("No se puede utilizar contraseñas por defecto");
  }

  @Test
  public void validarUsuarioYaRegistrado() throws IOException {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();

    registroUsuarios.guardarUsuario("admin","contraSuper*Segura20");

    assertThatThrownBy(() -> registroUsuarios.guardarUsuario("admin","contrapocoSegura01"))
        .isExactlyInstanceOf(UsuarioNoDisponibleExeption.class)
        .hasMessage("El usuario: admin no esta disponible");
  }

  @Test
  public void registrarYVerificar() throws IOException {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();

    registroUsuarios.guardarUsuario("usuario","contraSuper*Segura20");

    assertThat(registroUsuarios.validarUsuario("usuario", "contraSuper*Segura20")).isTrue();
  }

  @BeforeAll
  public static void vaciarArchivoUsuarios() throws IOException {
    File registroDeUsuarios = new File("./RegistroUsuarios.txt");
    if (!registroDeUsuarios.exists()) {
      throw new NullPointerException("El Registro de Usuarios no Existe");
    }

    BufferedWriter registoParaBorrar = new BufferedWriter(new FileWriter(registroDeUsuarios));
    registoParaBorrar.write("");
    registoParaBorrar.close();
  }

}
