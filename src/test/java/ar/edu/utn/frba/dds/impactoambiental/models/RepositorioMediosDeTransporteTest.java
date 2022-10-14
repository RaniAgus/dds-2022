package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.RepositorioMediosDeTransporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositorioMediosDeTransporteTest extends BaseTest {
  RepositorioMediosDeTransporte repositorioMediosDeTransporte = RepositorioMediosDeTransporte.getInstance();
  @BeforeEach
  private void cleanRepo() {
    repositorioMediosDeTransporte.limpiar();
    entityManager().flush();
    entityManager().clear();
  }
  @Test
  public void sePuedeAgregarUnMedioDeTransporte() {
    MedioDeTransporte medioDeTransporte = new MedioDeTransporte("Test",null,null,null);
    repositorioMediosDeTransporte.agregar(medioDeTransporte);
    assertEquals(medioDeTransporte, repositorioMediosDeTransporte.obtenerPorID(medioDeTransporte.getId()).orElse(null));
  }
  @Test
  public void sePuedenObtenerTodosLosMediosDeTransporte() {
    MedioDeTransporte medioDeTransporte = new MedioDeTransporte("Test",null,null,null);
    MedioDeTransporte otroMedioDeTransporte = new MedioDeTransporte("Test2",null,null,null);
    repositorioMediosDeTransporte.agregar(medioDeTransporte);
    repositorioMediosDeTransporte.agregar(otroMedioDeTransporte);

    assertEquals(asList(medioDeTransporte,otroMedioDeTransporte), repositorioMediosDeTransporte.obtenerTodos());
  }
}
