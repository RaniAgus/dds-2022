package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.FormularioLogin;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class ValidadorDeUsuarioTest extends BaseTest {
  @Test
  public void unaContraseniaValidaDevuelveUnResultadoValidoAlValidar() {
    Validador<FormularioLogin> validador = new Validador<>(new FormularioLogin("user", "password"))
        .agregarValidaciones(todasLasValidaciones());

    Either<FormularioLogin> resultado = validador.validar();

    assertThat(resultado.esExitoso()).isTrue();
  }

  @Test
  public void unaContraseniaInvalidaDevuelveUnResultadoFallidoAlValidar() {
    Validador<FormularioLogin> validador = new Validador<>(new FormularioLogin("user", "111"))
        .agregarValidaciones(todasLasValidaciones());
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.singletonList("111"));

    Either<FormularioLogin> resultado = validador.validar();

    assertThat(resultado.esExitoso()).isFalse();
    assertThat(resultado.getErrores()).containsExactlyInAnyOrder(
        "La contraseña debe tener al menos 8 caracteres.",
        "La contraseña no debe repetir 3 veces seguidas un mismo caracter.",
        "Contraseña dentro de las 10000 mas usadas. Elija otra por favor.");
  }
}
