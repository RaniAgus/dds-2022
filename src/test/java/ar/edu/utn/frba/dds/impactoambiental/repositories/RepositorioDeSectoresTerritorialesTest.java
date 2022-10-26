package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.impactoambiental.models.BaseTest;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioDeSectoresTerritorialesTest extends BaseTest implements PersistenceTest {
  RepositorioDeSectoresTerritoriales repositorioDeSectoresTerritoriales = RepositorioDeSectoresTerritoriales.getInstance();

  @Test
  public void sePuedeGuardarUnSectorTerritorial() {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("Test", new ArrayList<>());
    repositorioDeSectoresTerritoriales.agregar(sectorTerritorial);
    SectorTerritorial actualSectorTerritorial= repositorioDeSectoresTerritoriales.obtenerPorID(sectorTerritorial.getId()).orElse(null);
    assertEquals(sectorTerritorial, actualSectorTerritorial);
  }

  @Test
  public void sePuedenObtenerTodosLosSectores() {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("Test", new ArrayList<>());
    SectorTerritorial otroSectorTerritorial= new SectorTerritorial("Test2",new ArrayList<>());

    repositorioDeSectoresTerritoriales.agregar(sectorTerritorial);
    repositorioDeSectoresTerritoriales.agregar(otroSectorTerritorial);

    List<SectorTerritorial> actual = repositorioDeSectoresTerritoriales.obtenerTodos();
    assertEquals(asList(sectorTerritorial,otroSectorTerritorial),actual);
  }

  @BeforeEach
  private void cleanRepo() {
    repositorioDeSectoresTerritoriales.limpiar();
  }


}
