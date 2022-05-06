package models;

import models.exceptions.ContrasenaDebilException;
import models.RegistroUsuarios;
import models.exceptions.UsuarioNoDisponibleExeption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RegistroUsuarioTest {

  @Test
  public void validarContrasenasDebiles() {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();
    ContrasenaDebilException contrasenaCorta = Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.verificarContrasenaDebil("corta"));
    assertEquals(contrasenaCorta.getMessage(),"La contraseña debe tener al menos 8 caracteres");
    ContrasenaDebilException consecutivePasswordsCreciente = Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.verificarContrasenaDebil("1234567890"));
    assertEquals(consecutivePasswordsCreciente.getMessage(),"La contraseña no debe tener 4 caracteres consecutivos");
    ContrasenaDebilException consecutivePasswords = Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.verificarContrasenaDebil("edcbajuhy"));
    assertEquals(consecutivePasswords.getMessage(),"La contraseña no debe tener 4 caracteres consecutivos");
    ContrasenaDebilException repetitivePassword = Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.verificarContrasenaDebil("holaaatodooos"));
    assertEquals(repetitivePassword.getMessage(),"La contraseña no debe repetir 3 veces seguidas un caracter. Secuencia encontrada: aaa");
    ContrasenaDebilException contrasenasMasUsadas = Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.verificarContrasenaDebil("iloveyou"));
    assertEquals(contrasenasMasUsadas.getMessage(),"Contraseña dentro de las 10000 mas usadas. Elija otra por favor");
  }
  @Test
  public void validarContrasenaUsarioPorDefecto() {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();
    ContrasenaDebilException contrasenaPorDefecto = Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.guardarUsuario("usuario","usuario"));
    assertEquals(contrasenaPorDefecto.getMessage(),"No se puede utilizar contraseñas por defecto");
  }

  @Test
  public void validarUsuarioYaRegistrado() throws IOException {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();
    registroUsuarios.guardarUsuario("admin","contraSuper*Segura20");
    UsuarioNoDisponibleExeption usuarioNoDisponible = Assertions.assertThrows(UsuarioNoDisponibleExeption.class,()->registroUsuarios.guardarUsuario("admin","contrapocoSegura01"));
    assertEquals(usuarioNoDisponible.getMessage(),"El usuario: admin no esta disponible");
  }

  @Test
  public void registrarYVerificar() throws IOException {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.instance();
    registroUsuarios.guardarUsuario("usuario","contraSuper*Segura20");
    assertTrue(registroUsuarios.validarUsuario("usuario", "contraSuper*Segura20"));
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
