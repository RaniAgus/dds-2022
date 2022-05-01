import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class RegistroUsuarios {
  private Map<String, String> usuariosCreados;
  private List<String> contrasenasProhibidas;
  private static final RegistroUsuarios INSTANCE = new RegistroUsuarios();

  public static RegistroUsuarios instance() {
    return INSTANCE;
  }

  private RegistroUsuarios()  {
    this.contrasenasProhibidas = new ArrayList<String>();
    this.usuariosCreados = new HashMap<String, String>();

    File listado = new File("./Weak_Paswords.txt");
    Scanner contrasenasDebiles = null;
    try {
      contrasenasDebiles = new Scanner(listado);
    } catch (FileNotFoundException e) {
      throw new NullPointerException("no se encontro el archivo Weak_Paswords.txt");
    }

    while (contrasenasDebiles.hasNextLine()) {
      this.contrasenasProhibidas.add(contrasenasDebiles.nextLine());
    }
    contrasenasDebiles.close();

    File registroDeUsuarios = new File("./RegistroUsuarios.txt");
    Scanner usuariosRegistrados = null;
    try {
      usuariosRegistrados = new Scanner(registroDeUsuarios);
    } catch (FileNotFoundException e) {
      throw new NullPointerException("no se encontro el archivo RegistroUsuarios.txt.txt");
    }

    while (usuariosRegistrados.hasNextLine()) {
      String[] usuarioSinFormato = usuariosRegistrados.nextLine().split(": ");
      this.usuariosCreados.put(usuarioSinFormato[0], usuarioSinFormato[1]);
    }
    usuariosRegistrados.close();

  }

  public void verificarContrasenaDebil(String contrasena) {
    if (this.contrasenasProhibidas.contains(contrasena)) {
      throw new ContrasenaDebilException("Contraseña dentro de las 10000 mas usadas."
          + " Elija otra por favor");
    }
    if (contrasena.length() < 8) {
      throw new ContrasenaDebilException("La contraseña debe tener al menos 8 caracteres");
    }
  }

  public void guardarUsuario(String usuario, String contrasena) throws IOException {

    if (usuario.equals(contrasena)) {
      throw new ContrasenaDebilException("No se puede utilizar contraseñas ppor defecto");
    }
    this.verificarContrasenaDebil(contrasena);
    this.verificarUsuarioDisponible(usuario);
    this.usuariosCreados.put(usuario, sha256Hex(contrasena));

    File registroDeUsuarios = new File("./RegistroUsuarios.txt");
    if (!registroDeUsuarios.exists()) {
      throw new NullPointerException("El Registro de Usuarios no Existe");
    }

    BufferedWriter registoParaEscribir = new BufferedWriter(
        new FileWriter(registroDeUsuarios.getAbsoluteFile(), true));
    registoParaEscribir.write(usuario + ": " + sha256Hex(contrasena) + "\n");
    registoParaEscribir.close();
  }

  public void verificarUsuarioDisponible(String usuario) throws FileNotFoundException {
    if (this.usuariosCreados.containsKey(usuario)) {
      throw new UsuarioNoDisponibleExeption("El usuario: " + usuario + " no esta disponible");
    }
  }

  public boolean validarUsuario(String usuario, String contrasena) {
    return this.usuariosCreados.getOrDefault(usuario, "").equals(sha256Hex(contrasena));
  }

}
