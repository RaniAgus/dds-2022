package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.ValidacionDeUsuario;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Validar10MilContrasenas;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Validar8Caracteres;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.ValidarCaracteresConsecutivos;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.ValidarCaracteresRepetidos;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.ValidarUsuarioPorDefecto;
import java.util.Collections;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class ValidacionDeUsuarioTest extends BaseTest {
  // Validar 8 caracteres

  @Test
  public void unaContraseniaEsValidaSiTieneMasDeOchoCaracteres() {
    ValidacionDeUsuario validacion = new Validar8Caracteres();

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "12345678"));

    assertThat(resultado.esExitoso()).isTrue();
  }

  @Test
  public void unaContraseniaNoEsValidaSiTieneMenosDeOchoCaracteres() {
    ValidacionDeUsuario validacion = new Validar8Caracteres();

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "1234567"));

    assertThat(resultado.esExitoso()).isFalse();
  }

  // Validar 10.000 más usadas

  @Test
  public void unaContraseniaEsValidaSiNoSeEncuentraEntreLasDiezMilMasUsadas() {
    ValidacionDeUsuario validacion = new Validar10MilContrasenas(lector);
    when(lector.getLineas()).thenReturn(Collections.emptyList());

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "password"));

    assertThat(resultado.esExitoso()).isTrue();
  }

  @Test
  public void unaContraseniaNoEsValidaSiSeEncuentraEntreLasDiezMilMasUsadas() {
    ValidacionDeUsuario validacion = new Validar10MilContrasenas(lector);
    when(lector.getLineas()).thenReturn(Collections.singletonList("password"));

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "password"));

    assertThat(resultado.esExitoso()).isFalse();
  }

  // Caracteres repetidos

  @Test
  public void unaContraseniaEsValidaSiNoTieneTresCaracteresRepetidos() {
    ValidacionDeUsuario validacion = new ValidarCaracteresRepetidos();

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "11223344"));

    assertThat(resultado.esExitoso()).isTrue();
  }

  @Test
  public void unaContraseniaNoEsValidaSiTieneTresCaracteresRepetidos() {
    ValidacionDeUsuario validacion = new ValidarCaracteresRepetidos();

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "111"));

    assertThat(resultado.esExitoso()).isFalse();
  }

  // Caracteres consecutivos

  @Test
  public void unaContraseniaEsValidaSiNoTieneCuatroCaracteresConsecutivos() {
    ValidacionDeUsuario validacion = new ValidarCaracteresConsecutivos();

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "123123"));

    assertThat(resultado.esExitoso()).isTrue();
  }

  @Test
  public void unaContraseniaNoEsValidaSiTieneCuatroCaracteresConsecutivos() {
    ValidacionDeUsuario validacion = new ValidarCaracteresConsecutivos();

    Either<UsuarioDto> resultado1 = validacion.validar(new UsuarioDto("user", "1234"));
    Either<UsuarioDto> resultado2 = validacion.validar(new UsuarioDto("user", "4321"));

    SoftAssertions soft = new SoftAssertions();
    soft.assertThat(resultado1.esExitoso()).isFalse();
    soft.assertThat(resultado2.esExitoso()).isFalse();
    soft.assertAll();
  }

  // Usuario por defecto

  @Test
  public void unaContraseniaEsValidaSiNoEsIgualAlNombreDeUsuario() {
    ValidacionDeUsuario validacion = new ValidarUsuarioPorDefecto();

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "usern't"));

    assertThat(resultado.esExitoso()).isTrue();
  }

  @Test
  public void unaContraseniaNoEsValidaSiEsIgualAlNombreDeUsuario() {
    ValidacionDeUsuario validacion = new ValidarUsuarioPorDefecto();

    Either<UsuarioDto> resultado = validacion.validar(new UsuarioDto("user", "user"));

    assertThat(resultado.esExitoso()).isFalse();
  }
}
