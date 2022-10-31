package ar.edu.utn.frba.dds.impactoambiental.controllers;

import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.FormularioLogin;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioValidacionesDeUsuario;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuarioController {
  private RepositorioUsuarios usuarios = RepositorioUsuarios.getInstance();

  private RepositorioValidacionesDeUsuario validaciones = RepositorioValidacionesDeUsuario.getInstance();

  public ModelAndView crearUsuario(Request req, Response res) {
    return FormularioLogin.parsear(Form.of(req))
        .flatMap(formulario -> new Validador<>(formulario)
            .agregarValidaciones(new ArrayList<>(validaciones.obtenerTodos()))
            .validar())
        .fold(
            errores -> {
              errores.isEmpty();
              // TODO: Manejo de errores
              return null;
            },
            formularioLogin -> {
              // TODO: Crear usuario
              return null;
            }
        );
  }

  public ModelAndView loguearUsuario(Request req, Response res) {
    return FormularioLogin.parsear(Form.of(req))
        .flatMap(formulario -> usuarios.obtenerUsuario(
            formulario.getUsuario(),
            formulario.getContrasena()))
        .fold(
            errores -> {
              errores.isEmpty();
              // TODO: Manejo de errores
              return null;
            },
            usuario -> {
              usuario.getUsuario();
              usuario.getContrasena();
              // TODO: Loguear usuario
              return null;
            }
        );
  }
}
