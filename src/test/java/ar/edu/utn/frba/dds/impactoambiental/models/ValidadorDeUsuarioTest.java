package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import io.vavr.control.Either;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ValidadorDeUsuarioTest extends BaseTest {
  @Test
  public void unaContraseniaValidaDevuelveUnResultadoValidoAlValidar() {
    Validador<UsuarioDto> validador = crearValidadorConTodasLasValidaciones();

    Either<List<String>, UsuarioDto> resultado = validador.validar(new UsuarioDto("user", "password"));

    assertThat(resultado.isRight()).isTrue();
  }

  @Test
  public void unaContraseniaInvalidaDevuelveUnResultadoFallidoAlValidar() {
    Validador<UsuarioDto> validador = crearValidadorConTodasLasValidaciones();
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.singletonList("111"));

    Either<List<String>, UsuarioDto> resultado = validador.validar(new UsuarioDto("user", "111"));

    assertThat(resultado.isLeft()).isTrue();
    assertThat(resultado.getLeft()).containsExactlyInAnyOrder(
        "La contraseña debe tener al menos 8 caracteres.",
        "La contraseña no debe repetir 3 veces seguidas un mismo caracter.",
        "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.");
  }
}
