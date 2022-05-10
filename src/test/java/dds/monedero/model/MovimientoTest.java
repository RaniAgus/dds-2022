package dds.monedero.model;

import dds.monedero.exceptions.MontoNegativoException;
import org.junit.jupiter.api.Test;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

public class MovimientoTest {
  @Test
  void noSePuedeCrearUnMovimientoConMontoNegativo() {
    assertThrows(
        MontoNegativoException.class, () -> new Deposito(-1500.00, now())
    );
  }

  @Test
  void elValorDeUnDepositoEsPositivo() {
    Deposito deposito = new Deposito(100.00, now());

    assertEquals(100, deposito.getValor());
  }

  @Test
  void elValorDeUnaExtraccionEsNegativo() {
    Extraccion extraccion = new Extraccion(100.00, now());

    assertEquals(-100, extraccion.getValor());
  }

  @Test
  void unDepositoFueDepositadoALaFechaCorrespondiente() {
    Deposito deposito = new Deposito(100.00, now());

    assertTrue(deposito.fueDepositadoA(now()));
  }

  @Test
  void unDepositoNoFueDepositadoCualquierOtraFecha() {
    Deposito deposito = new Deposito(100.00, now());

    assertFalse(deposito.fueDepositadoA(now().minusDays(1)));
  }

  @Test
  void unDepositoNoSeExtrae() {
    Deposito deposito = new Deposito(100.00, now());

    assertFalse(deposito.fueExtraidoA(now()));
  }

  @Test
  void unaExtraccionFueExtraidaALaFechaCorrespondiente() {
    Extraccion extraccion = new Extraccion(100.00, now());

    assertTrue(extraccion.fueExtraidoA(now()));
  }

  @Test
  void unaExtraccionNoFueExtraidaCualquierOtraFecha() {
    Extraccion extraccion = new Extraccion(100.00, now());

    assertFalse(extraccion.fueExtraidoA(now().minusDays(1)));
  }

  @Test
  void unaExtraccionNoSeDeposita() {
    Extraccion extraccion = new Extraccion(100.00, now());

    assertFalse(extraccion.fueDepositadoA(now()));
  }
}
