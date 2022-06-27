package models;

import exceptions.ContrasenaDebilException;
import exceptions.UsuarioNoDisponibleExeption;
import models.validador.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class RegistroUsuarioTest {

  @Test
  public void validarContrasenasDebiles()  {
    SoftAssertions soft = new SoftAssertions();

    soft.assertThatThrownBy(() -> new Administrador(validador()
            ,"admin1234"
            ,"admin1234"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("Contraseña dentro de las 10000 mas usadas. Elija otra por favor."
            + "La contraseña no debe tener 4 caracteres consecutivos."
            +"No se puede utilizar contraseñas por defecto.");

    soft.assertThatThrownBy(() -> new Administrador(validador()
        ,"admin"
        ,"aaa1234"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessage("La contraseña debe tener al menos 8 caracteres."
            + "La contraseña no debe tener 4 caracteres consecutivos."
            + "La contraseña no debe repetir 3 veces seguidas un mismo caracter.");

    soft.assertAll();
  }


  @Test
  public void registrarYObtener() throws FileNotFoundException {
    Administradores administradores = Administradores.getInstance();
    Administrador administrador = new Administrador(validador(), "Juancito", "ContraSUper*MegaS3gUr4");

    administradores.agregarAdministrador(administrador);

    SoftAssertions soft = new SoftAssertions();

    soft.assertThat(administradores.obtenerAdministrador("Juancito","ContraSUper*MegaS3gUr4"))
        .isEqualTo(administrador);

    soft.assertThatThrownBy(() -> administradores.obtenerAdministrador("Juancito","contraIncorrecta"))
        .isExactlyInstanceOf(UsuarioNoDisponibleExeption.class)
        .hasMessage("No se pudo validar que sea ese administrador");

    soft.assertThatThrownBy(() -> administradores.obtenerAdministrador("Usuario_Inexistente","contraIncorrecta"))
        .isExactlyInstanceOf(UsuarioNoDisponibleExeption.class)
        .hasMessage("No existe el usuario: Usuario_Inexistente");

    soft.assertAll();
  }

  @BeforeEach
  public void limpiarSingleton() {
    Administradores.getInstance().admins = new ArrayList<>();
  }

  private Validador validador() throws FileNotFoundException {
    ArrayList<Validacion> listaValidaciones = new ArrayList<>();
    listaValidaciones.add(new Validar8Caracteres());
    listaValidaciones.add(new Validar10MilContrasenas());
    listaValidaciones.add(new ValidarCaracteresConsecutivos());
    listaValidaciones.add(new ValidarCaracteresRepetidos());
    listaValidaciones.add(new ValidarUsuarioPorDefecto());
    return new Validador(listaValidaciones);
  }
}
