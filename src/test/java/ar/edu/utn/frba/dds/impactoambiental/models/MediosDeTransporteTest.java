package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MediosDeTransporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MediosDeTransporteTest extends BaseTest {
  MediosDeTransporte mediosDeTransporte = MediosDeTransporte.getInstance();
  @BeforeEach
  private void cleanRepo() {
    mediosDeTransporte.limpiar();
    entityManager().flush();
    entityManager().clear();
  }
  @Test
  public void sePuedeAgregarUnMedioDeTransporte() {
    MedioDeTransporte medioDeTransporte = new MedioDeTransporte("Test",null,null,null);
    mediosDeTransporte.agregar(medioDeTransporte);
    assertEquals(medioDeTransporte,mediosDeTransporte.obtenerPorID(medioDeTransporte.getId()).orElse(null));
  }
  @Test
  public void sePuedenObtenerTodosLosMediosDeTransporte() {
    MedioDeTransporte medioDeTransporte = new MedioDeTransporte("Test",null,null,null);
    MedioDeTransporte otroMedioDeTransporte = new MedioDeTransporte("Test2",null,null,null);
    mediosDeTransporte.agregar(medioDeTransporte);
    mediosDeTransporte.agregar(otroMedioDeTransporte);

    assertEquals(asList(medioDeTransporte,otroMedioDeTransporte),mediosDeTransporte.obtenerTodos());
  }
}
