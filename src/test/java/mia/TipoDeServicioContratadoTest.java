package mia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoDeServicioContratadoTest {

  @Test
  public void sePuedeCrearNuevosTiposDeServicioContratado() {
    TipoDeServicioContratado uber = new TipoDeServicioContratado("Uber");
    assertEquals("Uber", uber.getNombre());
  }
}
