package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class ValidadorDeUsuarioTest extends BaseTest {
  @Test
  public void unaContraseniaValidaDevuelveUnResultadoValidoAlValidar() {
    Validador<UsuarioDto> validador = new Validador<>(new UsuarioDto("user", "password"))
        .agregarValidaciones(todasLasValidaciones());

    Either<UsuarioDto> resultado = validador.validar();

    assertThat(resultado.esExitoso()).isTrue();
  }

  @Test
  public void unaContraseniaInvalidaDevuelveUnResultadoFallidoAlValidar() {
    Validador<UsuarioDto> validador = new Validador<>(new UsuarioDto("user", "111"))
        .agregarValidaciones(todasLasValidaciones());
    when(lector.getLineas()).thenReturn(Collections.singletonList("111"));

    Either<UsuarioDto> resultado = validador.validar();

    assertThat(resultado.esExitoso()).isFalse();
    assertThat(resultado.getErrores()).containsExactlyInAnyOrder(
        "La contraseña debe tener al menos 8 caracteres.",
        "La contraseña no debe repetir 3 veces seguidas un mismo caracter.",
        "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.");
  }
}
