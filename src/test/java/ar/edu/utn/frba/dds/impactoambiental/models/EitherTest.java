package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class EitherTest extends BaseTest {
  @Test
  public void sePuedenColectarErroresDeMultiplesTries() {
    Either<String> resultado1 = Either.fallido(Collections.singletonList("Error 1"));
    Either<String> resultado2 = Either.fallido(Arrays.asList("Error 2", "Error 3"));
    Either<String> resultado3 = Either.exitoso("Resultado exitoso");

    assertThat(Either.colectarErrores(Arrays.asList(resultado1, resultado2, resultado3)))
        .containsExactly("Error 1", "Error 2", "Error 3");
  }

  @Test
  public void sePuedeMapearUnTryDeUnTipoAOtroExitosamente() {
    Either<String> resultado = Either.exitoso("6");

    Either<Integer> resultadoMapeado = resultado.apply(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getValor()).isEqualTo(6);
  }

  @Test
  public void sePuedeMapearUnTryDeUnTipoAOtroConError() {
    Either<String> resultado = Either.exitoso("6a");

    Either<Integer> resultadoMapeado = resultado.apply(Integer::parseInt, "El valor no es numérico");

    assertThat(resultadoMapeado.getErrores()).containsExactly("El valor no es numérico");
  }

  @Test
  public void unTryFallidoNoEsAfectadoPorElMapeo() {
    Either<String> resultado = Either.fallido("Error");

    Either<Integer> resultadoMapeado = resultado.apply(Integer::parseInt, "El valor no es numérico");

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

    assertThatThrownBy(resultado::getValor).isInstanceOf(NoSuchElementException.class);
  }

  @Test
  public void sePuedeFoldearUnEitherExitoso() {
    Either<String> resultado = Either.exitoso("6");

    Integer resultadoFoldeado = resultado.fold(errores -> 0, Integer::parseInt);

    assertThat(resultadoFoldeado).isEqualTo(6);
  }

  @Test
  public void sePuedeFoldearUnEitherFallido() {
    Either<String> resultado = Either.fallido("Error");

    Integer resultadoFoldeado = resultado.fold(errores -> 0, Integer::parseInt);

    assertThat(resultadoFoldeado).isEqualTo(0);
  }

  @Test
  public void sePuedeAplanarUnEitherExitoso() {
    Either<String> resultado = Either.exitoso("6");

    Either<Integer> resultadoAplanado = resultado.flatMap(valor -> Either.exitoso(Integer.parseInt(valor)));

    assertThat(resultadoAplanado.getValor()).isEqualTo(6);
  }

  @Test
  public void sePuedeAplanarUnEitherExitosoAFallido() {
    Either<String> resultado = Either.exitoso("6a");

    Either<Integer> resultadoAplanado = resultado.flatMap(valor -> Either.fallido("Error"));

    assertThat(resultadoAplanado.getErrores()).containsExactly("Error");
  }

  @Test
  public void sePuedeAplanarUnEitherFallido() {
    Either<String> resultado = Either.fallido("Error");

    Either<Integer> resultadoAplanado = resultado.flatMap(valor -> Either.exitoso(Integer.parseInt(valor)));

    assertThat(resultadoAplanado.getErrores()).containsExactly("Error");
  }

  @Test
  public void sePuedeConcatenarResultadosExitosos() {
    Either<Integer> resultado1 = Either.exitoso(6);
    Either<Integer> resultado2 = Either.exitoso(7);

    Either<Integer> resultadoConcatenado = Either.concatenar(() -> resultado1.getValor() + resultado2.getValor(), resultado1, resultado2);

    assertThat(resultadoConcatenado.getValor()).isEqualTo(13);
  }

  @Test
  public void sePuedeConcatenarResultadosFallidos() {
    Either<Integer> resultado1 = Either.fallido("Error 1");
    Either<Integer> resultado2 = Either.fallido("Error 2");

    Either<Integer> resultadoConcatenado = Either.concatenar(() -> resultado1.getValor() + resultado2.getValor(), resultado1, resultado2);

    assertThat(resultadoConcatenado.getErrores()).containsExactly("Error 1", "Error 2");
  }

  @Test
  public void sePuedeObtenerUnEitherDesdeUnaFuncionQueDevuelveOptional() {
    Either<String> resultado = Either.exitoso(Stream.of("1", "2", "3"))
            .flatApply(Stream::findFirst, "No se encontró el valor");

    assertThat(resultado.getValor()).isEqualTo("1");
  }

  @Test
  public void sePuedeObtenerUnEitherDesdeUnaFuncionQueDevuelveOptionalConError() {
    Either<String> resultado = Either.exitoso(Stream.<String>empty())
            .flatApply(Stream::findFirst, "No se encontró el valor");

    assertThat(resultado.getErrores()).containsExactly("No se encontró el valor");
  }
}
