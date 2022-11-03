package ar.edu.utn.frba.dds.impactoambiental.controllers.helpers;

import ar.edu.utn.frba.dds.impactoambiental.controllers.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioValidacionesDeUsuario;
import java.util.ArrayList;

public class UsuariosHelper {
  private RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstance();
  private RepositorioValidacionesDeUsuario validaciones = RepositorioValidacionesDeUsuario.getInstance();

  public Either<UsuarioDto> parsearUsuarioDto(Form form) {
    Either<String> username = form.getParamOrError("username", "El usuario es requerido");
    Either<String> password = form.getParamOrError("password", "La contraseña es requerida");

    return Either.concatenar(() -> new UsuarioDto(username.getValor(), password.getValor()), username, password);
  }

  public Either<Usuario> validarNuevoUsuario(Form form) {
    return parsearUsuarioDto(form)
        .flatMap(dto -> new Validador<>(dto)
            .agregarValidaciones(new ArrayList<>(validaciones.obtenerTodos()))
            .validar())
        .map(UsuarioDto::toEntity);
  }

  public Either<Usuario> obtenerUsuario(Form form) {
    return parsearUsuarioDto(form)
        .flatApply(dto -> repositorioUsuarios.obtenerUsuario(dto.getUsername(), dto.getPassword()), "Usuario o contraseña incorrectos");
  }
}
