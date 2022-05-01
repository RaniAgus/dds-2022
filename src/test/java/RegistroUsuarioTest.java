import org.junit.jupiter.api.Assertions;
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
    RegistroUsuarios registroUsuarios = RegistroUsuarios.INSTANCE;
    Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.verificarContrasenaDebil("corta"),"La contraseña debe tener al menos 8 caracteres");
    Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.verificarContrasenaDebil("1234567890"),"Contraseña dentro de las 10000 mas usadas. Elija otra por favor");
  }
  @Test
  public void validarContrasenaUsarioPorDefecto() {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.INSTANCE;
    Assertions.assertThrows(ContrasenaDebilException.class,()->registroUsuarios.guardarUsuario("usuario","usuario"),"No se puede utilizar contraseñas ppor defecto");
  }

  @Test
  public void validarUsuarioYaRegistrado() throws IOException {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.INSTANCE;
    registroUsuarios.guardarUsuario("admin","contraSuper*Segura20");
    Assertions.assertThrows(UsuarioNoDisponibleExeption.class,()->registroUsuarios.guardarUsuario("admin","contrapocoSegura01"),"El usuario: usuario no esta disponible");
  }

  @Test
  public void registrarYVerificar() throws IOException {
    RegistroUsuarios registroUsuarios = RegistroUsuarios.INSTANCE;
    registroUsuarios.guardarUsuario("usuario","contraSuper*Segura20");
    assertTrue(registroUsuarios.validarUsuario("usuario", "contraSuper*Segura20"));
  }

  @BeforeEach
  public void vaciarArchivoUsuarios() throws IOException {
    File registroDeUsuarios = new File("./RegistroUsuarios.txt");
    if (!registroDeUsuarios.exists()) {
      throw new NullPointerException("El Registro de Usuarios no Existe");
    }

    BufferedWriter registoParaBorrar = new BufferedWriter(new FileWriter(registroDeUsuarios));
    registoParaBorrar.write("");
    registoParaBorrar.close();
  }


}
