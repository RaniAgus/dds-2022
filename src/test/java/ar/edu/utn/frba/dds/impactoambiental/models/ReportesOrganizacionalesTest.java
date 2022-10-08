package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacional;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReportesOrganizacionales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportesOrganizacionalesTest extends BaseTest {
  ReportesOrganizacionales reportesOrganizacionales = ReportesOrganizacionales.getInstance();
  @BeforeEach
  public void cleanRepo(){
    reportesOrganizacionales.limpiar();
  }
  @Test
  public void sePuedeObtenerUnReportePorId() {
    ReporteOrganizacional reporte = new ReporteOrganizacional(null,null,new HashMap<>());
    reportesOrganizacionales.agregar(reporte);
    assertEquals(reporte, reportesOrganizacionales.obtenerPorID(reporte.getId()).orElse(null));
  }

  @Test
  public void sePuedenObtenerTodosLosReportes() {
    ReporteOrganizacional reporte = new ReporteOrganizacional(null,null,new HashMap<>());
    ReporteOrganizacional otroReporte = new ReporteOrganizacional(null,null,new HashMap<>());
    reportesOrganizacionales.agregar(reporte);
    reportesOrganizacionales.agregar(otroReporte);

    assertEquals(asList(reporte,otroReporte),reportesOrganizacionales.obtenerTodos());
  }

  @Test
  public void sePuedeObtenerUnReporteSegunSuPeriodo( ) {
    Organizacion organizacion = crearOrganizacionVacia();
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteOrganizacional reporte = new ReporteOrganizacional(organizacion,periodo,new HashMap<>());

    reportesOrganizacionales.agregar(reporte);
    assertEquals(0.0, reportesOrganizacionales.HCTotalTipoDeOrganizacion(periodo, TipoDeOrganizacion.INSTITUCION));
  }
  @Test
  public void sePuedeObtenerLaEvolucionDeUnSector( ) {
    Organizacion organizacion = crearOrganizacionVacia();
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteOrganizacional reporte = new ReporteOrganizacional(organizacion,periodo,new HashMap<>());

    reportesOrganizacionales.agregar(reporte);
    assertEquals(asList(reporte), reportesOrganizacionales.evolucionHCTotalOrganizaccion(organizacion));
  }
}
