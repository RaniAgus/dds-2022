package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PeriodoTest extends BaseTest {

  @Test
  void periodoMensualContieneFechasDentroDelMes() {
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    LocalDate primeraFecha = LocalDate.now();
    LocalDate ultimaFecha = LocalDate.now().plusMonths(1).minusDays(1);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(periodo.contieneFecha(primeraFecha)).isTrue();
    softly.assertThat(periodo.contieneFecha(ultimaFecha)).isTrue();
    softly.assertAll();
  }

  @Test
  void periodoMensualNoContieneFechasFueraDelMes() {
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    LocalDate primeraFecha = LocalDate.now().minusDays(1);
    LocalDate ultimaFecha = LocalDate.now().plusMonths(1);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(periodo.contieneFecha(primeraFecha)).isFalse();
    softly.assertThat(periodo.contieneFecha(ultimaFecha)).isFalse();
    softly.assertAll();
  }

  @Test
  void periodoAnualContieneFechasDentroDelAnio() {
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.ANUAL);
    LocalDate primeraFecha = LocalDate.now();
    LocalDate ultimaFecha = LocalDate.now().plusYears(1).minusDays(1);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(periodo.contieneFecha(primeraFecha)).isTrue();
    softly.assertThat(periodo.contieneFecha(ultimaFecha)).isTrue();
    softly.assertAll();
  }

  @Test
  void periodoAnualNoContieneFechasFueraDelAnio() {
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.ANUAL);
    LocalDate primeraFecha = LocalDate.now().minusDays(1);
    LocalDate ultimaFecha = LocalDate.now().plusYears(1);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(periodo.contieneFecha(primeraFecha)).isFalse();
    softly.assertThat(periodo.contieneFecha(ultimaFecha)).isFalse();
    softly.assertAll();
  }

  @Test
  void periodoEstaContenidoDentroDeOtro() {
    Periodo periodo1 = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    Periodo periodo2 = new Periodo(LocalDate.now().plusMonths(1).minusDays(1), Periodicidad.MENSUAL);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(periodo1.estaEnPeriodo(periodo2)).isTrue();
    softly.assertThat(periodo2.estaEnPeriodo(periodo1)).isTrue();
    softly.assertAll();
  }

  @Test
  void periodoNoEstaContenidoDentroDeOtro() {
    Periodo periodo1 = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    Periodo periodo2 = new Periodo(LocalDate.now().plusMonths(1), Periodicidad.MENSUAL);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(periodo1.estaEnPeriodo(periodo2)).isFalse();
    softly.assertThat(periodo2.estaEnPeriodo(periodo1)).isFalse();
    softly.assertAll();
  }
}
