package models;

import exceptions.ContrasenaDebilException;
import models.validador.Validador;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class ValidadorTest extends BaseTest {
  @Test
  void unaContraseniaValidaNoArrojaExcepcionAlValidar() {
    Validador validador = crearValidadorConTodasLasValidaciones();

    assertThatCode(() -> validador.validar("user", "password")).doesNotThrowAnyException();
  }

  @Test
  void unaContraseniaInvalidaArrojaExcepcionConTodosLosMensajesAlValidar() {
    Validador validador = crearValidadorConTodasLasValidaciones();
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.singletonList("111"));

    assertThatThrownBy(() -> validador.validar("user", "111"))
        .isExactlyInstanceOf(ContrasenaDebilException.class)
        .hasMessageContainingAll(
            "La contraseña debe tener al menos 8 caracteres.",
            "La contraseña no debe repetir 3 veces seguidas un mismo caracter.",
            "Contraseña dentro de las 10000 mas usadas. Elija otra por favor."
        );
  }
}
