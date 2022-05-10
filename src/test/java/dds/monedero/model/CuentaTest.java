package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CuentaTest {

  @Test
  void alPonerUnDepositoAumentaElSaldo() {
    Cuenta cuenta = cuentaNueva();

    cuenta.poner(1500);

    assertEquals(1500, cuenta.getSaldo());
  }

  @Test
  void alExtraerUnDepositoDisminuyeElSaldo() {
    Cuenta cuenta = cuentaConSaldoInicial(100);

    cuenta.sacar(75);

    assertEquals(25, cuenta.getSaldo());
  }

  @Test
  void sePuedePonerTresDepositosElMismoDia() {
    Cuenta cuenta = cuentaNueva();

    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);

    assertEquals(1500 + 456 + 1900, cuenta.getSaldo());
  }

  @Test
  void noSePuedePonerMasDeTresDepositosElMismoDia() {
    Cuenta cuenta = cuentaNueva();

    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);

    assertThrows(
        MaximaCantidadDepositosException.class, () -> cuenta.poner(245)
    );
  }

  @Test
  void noSePuedeExtraerUnMontoMayorQueElSaldo() {
    Cuenta cuenta = cuentaConSaldoInicial(90);

    assertThrows(SaldoMenorException.class, () -> cuenta.sacar(91));
  }

  @Test
  public void noSePuedeExtraerMasDeMilUnMismoDia() {
    Cuenta cuenta = cuentaConSaldoInicial(1500);

    cuenta.sacar(400);
    cuenta.sacar(400);

    assertThrows(
        MaximoExtraccionDiarioException.class, () -> cuenta.sacar(400)
    );
  }

  private Cuenta cuentaNueva() {
    return new Cuenta(emptyList());
  }

  private Cuenta cuentaConSaldoInicial(double montoInicial) {
    return new Cuenta(singletonList(
        new Deposito(montoInicial, LocalDate.now().minusDays(1))
    ));
  }

}
