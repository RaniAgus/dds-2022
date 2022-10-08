package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorial;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReportesSectoriales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportesSectorialesTest extends BaseTest {
  private ReportesSectoriales reportesSectoriales = ReportesSectoriales.getInstance();

  @Test
  public void sePuedeObtenerUnReportePorId() {
    ReporteSectorial reporte = new ReporteSectorial(null,null,new HashMap<>());
    reportesSectoriales.agregar(reporte);
    assertEquals(reporte, reportesSectoriales.obtenerPorID(reporte.getId()).orElse(null));
  }

  @Test
  public void sePuedenObtenerTodosLosReportes() {
    ReporteSectorial reporte = new ReporteSectorial(null,null,new HashMap<>());
    ReporteSectorial otroReporte = new ReporteSectorial(null,null,new HashMap<>());
    reportesSectoriales.agregar(reporte);
    reportesSectoriales.agregar(otroReporte);

    assertEquals(asList(reporte,otroReporte),reportesSectoriales.obtenerTodos());
  }

  @Test
  public void sePuedeObtenerUnReporteSegunSuPeriodo( ) {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("test", new ArrayList<>());
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteSectorial reporte = new ReporteSectorial(sectorTerritorial,periodo,new HashMap<>());

    reportesSectoriales.agregar(reporte);
    assertEquals(reporte, reportesSectoriales.reporteSectorialSegunPeriodo(periodo,sectorTerritorial).get());
  }
  @Test
  public void sePuedeObtenerLaEvolucionDeUnSector( ) {
    SectorTerritorial sectorTerritorial = new SectorTerritorial("test", new ArrayList<>());
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteSectorial reporte = new ReporteSectorial(sectorTerritorial,periodo,new HashMap<>());
    reportesSectoriales.agregar(reporte);
    assertEquals(asList(reporte), reportesSectoriales.evolucionHCTotalSector(sectorTerritorial));
  }

  @BeforeEach
  public void cleanRepo(){
    reportesSectoriales.limpiar();
  }
}
