package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ChequeoFallidoException;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Try;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class TryTest {
  @Test
  public void sePuedenColectarErroresDeMultiplesTries() {
    Try<String> resultado1 = Try.fallido(Collections.singletonList("Error 1"));
    Try<String> resultado2 = Try.fallido(Arrays.asList("Error 2", "Error 3"));
    Try<String> resultado3 = Try.exitoso("Resultado exitoso");

    assertThat(Try.colectarErrores(resultado1, resultado2, resultado3))
      .containsExactly("Error 1", "Error 2", "Error 3");
  }

  @Test
  public void sePuedenColectarErroresDeMultiplesTriesConRepetidos() {
    Try<String> resultado1 = Try.fallido(Collections.singletonList("Error 1"));
    Try<String> resultado2 = Try.fallido(Arrays.asList("Error 2", "Error 3"));

    assertThat(Try.colectarErrores(resultado1, resultado2, resultado2))
      .containsExactly("Error 1", "Error 2", "Error 3");
  }

  @Test
  public void sePuedeMapearUnTryDeUnTipoAOtroExitosamente() {
    Try<String> resultado = Try.exitoso("6");

    Try<Integer> resultadoMapeado = resultado.map(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getValor()).isEqualTo(6);
  }

  @Test
  public void sePuedeMapearUnTryDeUnTipoAOtroConError() {
    Try<String> resultado = Try.exitoso("6a");

    Try<Integer> resultadoMapeado = resultado.map(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getErrores()).containsExactly("El valor no es numérico");
  }

  @Test
  public void unTryFallidoNoEsAfectadoPorElMapeo() {
    Try<String> resultado = Try.fallido("Error");

    Try<Integer> resultadoMapeado = resultado.map(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getErrores()).containsExactly("Error");
  }

  @Test
  public void sePuedeParsearUnValorExitosamente() {
    Try<Integer> resultado = Try.desde(() -> Integer.parseInt("6"), "El valor no es numérico");

    assertThat(resultado.getValor()).isEqualTo(6);
  }

  @Test
  public void sePuedeParsearUnValorConError() {
    Try<Integer> resultado = Try.desde(() -> Integer.parseInt("6a"), "El valor no es numérico");

    assertThat(resultado.getErrores()).containsExactly("El valor no es numérico");
  }

  @Test
  public void noSePuedeObtenerElValorDeUnTryFallido() {
    Try<String> resultado = Try.fallido("Error");

    assertThatThrownBy(resultado::getValor)
        .isInstanceOf(ChequeoFallidoException.class)
        .extracting("try")
        .isEqualTo(resultado);
  }
}
