package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionFallidaException;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class EitherTest {
  @Test
  public void sePuedenColectarErroresDeMultiplesTries() {
    Either<String> resultado1 = Either.fallido(Collections.singletonList("Error 1"));
    Either<String> resultado2 = Either.fallido(Arrays.asList("Error 2", "Error 3"));
    Either<String> resultado3 = Either.exitoso("Resultado exitoso");

    assertThat(Either.colectarErrores(resultado1, resultado2, resultado3))
      .containsExactly("Error 1", "Error 2", "Error 3");
  }

  @Test
  public void sePuedenColectarErroresDeMultiplesTriesConRepetidos() {
    Either<String> resultado1 = Either.fallido(Collections.singletonList("Error 1"));
    Either<String> resultado2 = Either.fallido(Arrays.asList("Error 2", "Error 3"));

    assertThat(Either.colectarErrores(resultado1, resultado2, resultado2))
      .containsExactly("Error 1", "Error 2", "Error 3");
  }

  @Test
  public void sePuedeMapearUnTryDeUnTipoAOtroExitosamente() {
    Either<String> resultado = Either.exitoso("6");

    Either<Integer> resultadoMapeado = resultado.map(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getValor()).isEqualTo(6);
  }

  @Test
  public void sePuedeMapearUnTryDeUnTipoAOtroConError() {
    Either<String> resultado = Either.exitoso("6a");

    Either<Integer> resultadoMapeado = resultado.map(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getErrores()).containsExactly("El valor no es numérico");
  }

  @Test
  public void unTryFallidoNoEsAfectadoPorElMapeo() {
    Either<String> resultado = Either.fallido("Error");

    Either<Integer> resultadoMapeado = resultado.map(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getErrores()).containsExactly("Error");
  }

  @Test
  public void sePuedeParsearUnValorExitosamente() {
    Either<Integer> resultado = Either.desde(() -> Integer.parseInt("6"), "El valor no es numérico");

    assertThat(resultado.getValor()).isEqualTo(6);
  }

  @Test
  public void sePuedeParsearUnValorConError() {
    Either<Integer> resultado = Either.desde(() -> Integer.parseInt("6a"), "El valor no es numérico");

    assertThat(resultado.getErrores()).containsExactly("El valor no es numérico");
  }

  @Test
  public void noSePuedeObtenerElValorDeUnTryFallido() {
    Either<String> resultado = Either.fallido("Error");

    assertThatThrownBy(resultado::getValor)
        .isInstanceOf(ValidacionFallidaException.class)
        .extracting("either")
        .isEqualTo(resultado);
  }
}
