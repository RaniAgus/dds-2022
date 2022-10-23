package ar.edu.utn.frba.dds.impactoambiental.models;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorial;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioReportesSectoriales;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioReportesSectorialesTest extends BaseTest {
  private RepositorioReportesSectoriales repositorioReportesSectoriales = RepositorioReportesSectoriales.getInstance();

  @Test
  public void sePuedeObtenerUnReportePorId() {
    ReporteSectorial reporte = new ReporteSectorial(null,null,new HashMap<>());
    repositorioReportesSectoriales.agregar(reporte);
    assertEquals(reporte, repositorioReportesSectoriales.obtenerPorID(reporte.getId()).orElse(null));
  }

  @Test
  public void sePuedenObtenerTodosLosReportes() {
    ReporteSectorial reporte = new ReporteSectorial(null,null,new HashMap<>());
    ReporteSectorial otroReporte = new ReporteSectorial(null,null,new HashMap<>());
    repositorioReportesSectoriales.agregar(reporte);
    repositorioReportesSectoriales.agregar(otroReporte);

    assertEquals(asList(reporte,otroReporte), repositorioReportesSectoriales.obtenerTodos());
  }

  @Test
  public void sePuedeObtenerUnReporteSegunSuPeriodo( ) {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("test", new ArrayList<>());
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteSectorial reporte = new ReporteSectorial(sectorTerritorial,periodo,new HashMap<>());

    repositorioReportesSectoriales.agregar(reporte);
    assertEquals(reporte, repositorioReportesSectoriales.reporteSectorialSegunPeriodo(periodo,sectorTerritorial).get());
  }
  @Test
  public void sePuedeObtenerLaEvolucionDeUnSector( ) {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("test", new ArrayList<>());
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteSectorial reporte = new ReporteSectorial(sectorTerritorial,periodo,new HashMap<>());
    repositorioReportesSectoriales.agregar(reporte);
    assertEquals(asList(reporte), repositorioReportesSectoriales.evolucionHCTotalSector(sectorTerritorial));
  }

  @BeforeEach
  public void cleanRepo(){
    repositorioReportesSectoriales.limpiar();
  }
}
