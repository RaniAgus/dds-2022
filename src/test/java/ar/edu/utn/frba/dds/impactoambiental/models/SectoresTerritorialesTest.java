package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectoresTerritoriales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class SectoresTerritorialesTest extends BaseTest {
  SectoresTerritoriales sectoresTerritoriales = SectoresTerritoriales.getInstance();

  @Test
  public void sePuedeGuardarUnSectorTerritorial() {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("Test", new ArrayList<>());
    sectoresTerritoriales.agregar(sectorTerritorial);
    SectorTerritorial actualSectorTerritorial= sectoresTerritoriales.obtenerPorID(sectorTerritorial.getId());
    assertEquals(sectorTerritorial, actualSectorTerritorial);
  }

  @Test
  public void sePuedenObtenerTodosLosSectores() {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("Test", new ArrayList<>());
    SectorTerritorial otroSectorTerritorial= new SectorTerritorial("Test2",new ArrayList<>());

    sectoresTerritoriales.agregar(sectorTerritorial);
    sectoresTerritoriales.agregar(otroSectorTerritorial);

    List<SectorTerritorial> actual = sectoresTerritoriales.obtenerTodos();
    assertEquals(asList(sectorTerritorial,otroSectorTerritorial),actual);
  }

  @BeforeEach
  private  void cleanRepo() {
    sectoresTerritoriales.limpiar();
  }


}
