package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Lineas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineasTest extends BaseTest {
  Lineas lineas = Lineas.getInstance();

  @Test
  public void sePuedeObtenerUnaLineaPorId() {
    Linea unaLinea = crearLineaDeSubteVacia();
    lineas.agregar(unaLinea);
    assertEquals(unaLinea, lineas.obtenerPorID(unaLinea.getId()));
  }
  @Test
  public void sePuedeObtenerTodasLasLineas() {
    Linea unaLinea = crearLineaDeSubteVacia();
    Linea otraLinea = crearLineaDeSubteVacia();
    lineas.agregar(unaLinea);
    lineas.agregar(otraLinea);

    assertEquals(asList(unaLinea,otraLinea), lineas.obtenerTodos());
  }

  @BeforeEach
  private void cleanRepo() {
    lineas.limpiar();
    entityManager().flush();
    entityManager().clear();
  }
}
