package ar.edu.utn.frba.dds.impactoambiental.utils;

import static org.assertj.core.api.Assertions.assertThat;

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
}
