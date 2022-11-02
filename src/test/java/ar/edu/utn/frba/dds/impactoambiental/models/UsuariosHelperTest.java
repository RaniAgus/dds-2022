package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.controllers.helpers.UsuariosHelper;
import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.Form;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuariosHelperTest extends BaseTest {
  private Form form;
  private UsuariosHelper helper;

  @BeforeEach
  public void mockearForm() {
    form = mock(Form.class);
    helper = new UsuariosHelper();
  }

  @Test
  public void sePuedeParsearUnFormularioLoginExitosamente() {
    when(form.getParamOrError("username", "El usuario es requerido"))
        .thenReturn(Either.exitoso("Juancito"));
    when(form.getParamOrError("password", "La contraseña es requerida"))
        .thenReturn(Either.exitoso("ContraSUper*MegaS3gUr4"));

    Either<UsuarioDto> formularioLogin = helper.parsearUsuarioDto(form);

    assertThat(formularioLogin.getValor().getUsername()).isEqualTo("Juancito");
    assertThat(formularioLogin.getValor().getPassword()).isEqualTo("ContraSUper*MegaS3gUr4");
  }

  @Test
  public void sePuedeParsearUnFormularioLoginFallido() {
    when(form.getParamOrError("username", "El usuario es requerido"))
        .thenReturn(Either.fallido("El usuario es requerido"));
    when(form.getParamOrError("password", "La contraseña es requerida"))
        .thenReturn(Either.fallido("La contraseña es requerida"));

    Either<UsuarioDto> formularioLogin = helper.parsearUsuarioDto(form);

    assertThat(formularioLogin.getErrores()).containsExactly("El usuario es requerido", "La contraseña es requerida");
  }

  @Test
  public void sePuedeParsearUnFormularioLoginFallidoPorqueFaltaElUsuario() {
    when(form.getParamOrError("username", "El usuario es requerido"))
        .thenReturn(Either.fallido("El usuario es requerido"));
    when(form.getParamOrError("password", "La contraseña es requerida"))
        .thenReturn(Either.exitoso("ContraSUper*MegaS3gUr4"));

    Either<UsuarioDto> formularioLogin = helper.parsearUsuarioDto(form);

    assertThat(formularioLogin.getErrores()).containsExactly("El usuario es requerido");
  }

  @Test
  public void sePuedeParsearUnFormularioLoginFallidoPorqueFaltaLaContrasena() {
    when(form.getParamOrError("username", "El usuario es requerido"))
        .thenReturn(Either.exitoso("Juancito"));
    when(form.getParamOrError("password", "La contraseña es requerida"))
        .thenReturn(Either.fallido("La contraseña es requerida"));

    Either<UsuarioDto> formularioLogin = helper.parsearUsuarioDto(form);

    assertThat(formularioLogin.getErrores()).containsExactly("La contraseña es requerida");
  }
}
