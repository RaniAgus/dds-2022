package models;

import models.validador.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ValidacionTest extends BaseTest {
  // Validar 8 caracteres

  @Test
  void unaContraseniaEsValidaSiTieneMasDeOchoCaracteres() {
    Validacion validacion = new Validar8Caracteres();

    Optional<String> resultado = validacion.validar("user", "12345678");

    assertThat(resultado.isPresent()).isFalse();
  }

  @Test
  void unaContraseniaNoEsValidaSiTieneMenosDeOchoCaracteres() {
    Validacion validacion = new Validar8Caracteres();

    Optional<String> resultado = validacion.validar("user", "1234567");

    assertThat(resultado.isPresent()).isTrue();
  }

  // Validar 10.000 más usadas

  @Test
  void unaContraseniaEsValidaSiNoSeEncuentraEntreLasDiezMilMasUsadas() {
    Validacion validacion = new Validar10MilContrasenas(lectorDeArchivos);
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.emptyList());

    Optional<String> resultado = validacion.validar("user", "password");

    assertThat(resultado.isPresent()).isFalse();
  }

  @Test
  void unaContraseniaNoEsValidaSiSeEncuentraEntreLasDiezMilMasUsadas() {
    Validacion validacion = new Validar10MilContrasenas(lectorDeArchivos);
    when(lectorDeArchivos.getLineas()).thenReturn(Collections.singletonList("password"));

    Optional<String> resultado = validacion.validar("user", "password");

    assertThat(resultado.isPresent()).isTrue();
  }

  // Caracteres repetidos

  @Test
  void unaContraseniaEsValidaSiNoTieneTresCaracteresRepetidos() {
    Validacion validacion = new ValidarCaracteresRepetidos();

    Optional<String> resultado = validacion.validar("user", "11223344");

    assertThat(resultado.isPresent()).isFalse();
  }

  @Test
  void unaContraseniaNoEsValidaSiTieneTresCaracteresRepetidos() {
    Validacion validacion = new ValidarCaracteresRepetidos();

    Optional<String> resultado = validacion.validar("user", "111");

    assertThat(resultado.isPresent()).isTrue();
  }

  // Caracteres consecutivos

  @Test
  void unaContraseniaEsValidaSiNoTieneCuatroCaracteresConsecutivos() {
    Validacion validacion = new ValidarCaracteresConsecutivos();

    Optional<String> resultado = validacion.validar("user", "123123");

    assertThat(resultado.isPresent()).isFalse();
  }

  @Test
  void unaContraseniaNoEsValidaSiTieneCuatroCaracteresConsecutivos() {
    Validacion validacion = new ValidarCaracteresConsecutivos();

    Optional<String> resultado1 = validacion.validar("user", "1234");
    Optional<String> resultado2 = validacion.validar("user", "4321");

    SoftAssertions soft = new SoftAssertions();
    soft.assertThat(resultado1.isPresent()).isTrue();
    soft.assertThat(resultado2.isPresent()).isTrue();
    soft.assertAll();
  }

  // Usuario por defecto

  @Test
  void unaContraseniaEsValidaSiNoEsIgualAlNombreDeUsuario() {
    Validacion validacion = new ValidarUsuarioPorDefecto();

    Optional<String> resultado = validacion.validar("user", "usern't");

    assertThat(resultado.isPresent()).isFalse();
  }

  @Test
  void unaContraseniaNoEsValidaSiEsIgualAlNombreDeUsuario() {
    Validacion validacion = new ValidarUsuarioPorDefecto();

    Optional<String> resultado = validacion.validar("user", "user");

    assertThat(resultado.isPresent()).isTrue();
  }
}
