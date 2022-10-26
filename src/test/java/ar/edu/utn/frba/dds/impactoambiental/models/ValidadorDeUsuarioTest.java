package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ChequeoFallidoException;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class ValidadorDeUsuarioTest extends BaseTest {
  @Test
  public void unaContraseniaValidaNoArrojaExcepcionAlValidar() {
    Validador<UsuarioDto> validador = crearValidadorConTodasLasValidaciones();

    assertThatCode(() -> validador.validar(new UsuarioDto("user", "password"))).doesNotThrowAnyException();
  }

  @Test
  public void unaContraseniaInvalidaArrojaExcepcionConTodosLosMensajesAlValidar() {
    Validador<UsuarioDto> validador = crearValidadorConTodasLasValidaciones();
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.singletonList("111"));

    assertThatThrownBy(() -> validador.validar(new UsuarioDto("user", "111")))
        .isExactlyInstanceOf(ChequeoFallidoException.class)
        .hasMessageContainingAll(
            "La contraseña debe tener al menos 8 caracteres.",
            "La contraseña no debe repetir 3 veces seguidas un mismo caracter.",
            "Contraseña dentro de las 10000 mas usadas. Elija otra por favor."
        );
  }
}
