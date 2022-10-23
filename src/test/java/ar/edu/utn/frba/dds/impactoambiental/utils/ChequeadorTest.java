package ar.edu.utn.frba.dds.impactoambiental.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ChequeadorTest {
  @Test
  public void sePuedeChequearUnStringCuandoEsValido() {
    Chequeador<String> chequeador = new Chequeador<>("campo");
    chequeador.agregarChequeo((valor) -> valor.length() > 5, "El valor debe tener más de 5 caracteres");

    Try<String> resultado = chequeador.chequearValor("123456");

    assertThat(resultado.getValor()).isEqualTo("123456");
  }

  @Test
  public void sePuedeChequearUnStringCuandoEsInvalido() {
    Chequeador<String> chequeador = new Chequeador<>("campo");
    chequeador
        .agregarChequeo((valor) -> valor.length() > 7, "El valor debe tener más de 7 caracteres")
        .agregarChequeo((valor) -> valor.length() > 5, "El valor debe tener más de 5 caracteres");

    Try<String> resultado = chequeador.chequearValor("123456");

    assertThat(resultado.getErrores()).containsExactly("El valor debe tener más de 7 caracteres");
  }

  @Test
  public void sePuedeChequearUnStringCuandoEsInvalidoPorMasDeUnMotivo() {
    Chequeador<String> chequeador = new Chequeador<>("campo");
    chequeador
        .agregarChequeo((valor) -> valor.length() > 8, "El valor debe tener más de 7 caracteres")
        .agregarChequeo((valor) -> valor.length() > 6, "El valor debe tener más de 5 caracteres")
        .agregarChequeo((valor) -> valor.length() > 4, "El valor debe tener más de 3 caracteres");

    Try<String> resultado = chequeador.chequearValor("123456");

    assertThat(resultado.getErrores()).containsExactly(
        "El valor debe tener más de 7 caracteres", "El valor debe tener más de 5 caracteres");
  }

  @Test
  public void sePuedeChequearUnStringCuandoEsNulo() {
    Chequeador<String> chequeador = new Chequeador<>("campo");
    chequeador
        .agregarChequeoNoNulo("El valor no puede ser nulo")
        .agregarChequeo((valor) -> valor.length() > 5, "El valor debe tener más de 5 caracteres");

    Try<String> resultado = chequeador.chequearValor(null);

    assertThat(resultado.getErrores()).containsExactly("El valor no puede ser nulo");
  }

  @Test
  public void sePuedeChequearUnStringCuandoEsNuloYNoHayChequeo() {
    Chequeador<String> chequeador = new Chequeador<>("campo");
    chequeador.agregarChequeo((valor) -> valor.length() > 5, "El valor debe tener más de 5 caracteres");

    Try<String> resultado = chequeador.chequearValor(null);

    assertThat(resultado.getValor()).isNull();
  }

  @Test
  public void sePuedeChequearUnCampoQueNoEsStringDadoUnString() {
    Chequeador<Integer> chequeador = new Chequeador<>("campo");
    chequeador.agregarChequeo((valor) -> valor > 5, "El valor debe ser mayor a 5");

    Try<Integer> resultado = chequeador.chequearValor("6", Integer::parseInt, "El valor no es un número");

    assertThat(resultado.getValor()).isEqualTo(6);
  }

  @Test
  public void sePuedeChequearUnCampoQueNoEsStringDadoUnStringInvalido() {
    Chequeador<Integer> chequeador = new Chequeador<>("campo");
    chequeador.agregarChequeo((valor) -> valor > 5, "El valor debe ser mayor a 5");

    Try<Integer> resultado = chequeador.chequearValor("6a", Integer::parseInt, "El valor no es un número");

    assertThat(resultado.getErrores()).containsExactly("El valor no es un número");
  }
}
