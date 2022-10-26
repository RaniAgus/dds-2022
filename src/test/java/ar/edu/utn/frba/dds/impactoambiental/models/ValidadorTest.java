package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ChequeoFallidoException;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Try;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import org.junit.jupiter.api.Test;

public class ValidadorTest {
  @Test
  public void sePuedeChequearCuandoEsValido() {
    Validador<String> chequeador = new Validador<>();
    chequeador.agregarValidacion((valor) -> valor.length() > 5, "El valor debe tener más de 5 caracteres");

    Try<String> resultado = chequeador.validar("123456");

    assertThat(resultado.getValor()).isEqualTo("123456");
  }

  @Test
  public void sePuedeChequearCuandoEsInvalido() {
    Validador<String> chequeador = new Validador<>();
    chequeador
        .agregarValidacion((valor) -> valor.length() > 7, "El valor debe tener más de 7 caracteres")
        .agregarValidacion((valor) -> valor.length() > 5, "El valor debe tener más de 5 caracteres");

    assertThatThrownBy(() -> chequeador.validar("123456"))
        .isInstanceOf(ChequeoFallidoException.class)
        .extracting("try.errores")
        .asList()
        .containsExactlyInAnyOrder("El valor debe tener más de 7 caracteres");
  }

  @Test
  public void sePuedeChequearCuandoEsInvalidoPorMasDeUnMotivo() {
    Validador<String> chequeador = new Validador<>();
    chequeador
        .agregarValidacion((valor) -> valor.length() > 8, "El valor debe tener más de 7 caracteres")
        .agregarValidacion((valor) -> valor.length() > 6, "El valor debe tener más de 5 caracteres")
        .agregarValidacion((valor) -> valor.length() > 4, "El valor debe tener más de 3 caracteres");

    assertThatThrownBy(() -> chequeador.validar("123456"))
        .isInstanceOf(ChequeoFallidoException.class)
        .extracting("try.errores")
        .asList()
        .containsExactlyInAnyOrder("El valor debe tener más de 7 caracteres", "El valor debe tener más de 5 caracteres");
  }

}
