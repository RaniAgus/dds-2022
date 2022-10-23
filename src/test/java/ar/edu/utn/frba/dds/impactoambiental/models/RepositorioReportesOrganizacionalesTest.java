package ar.edu.utn.frba.dds.impactoambiental.models;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacional;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioReportesOrganizacionales;
import java.time.LocalDate;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioReportesOrganizacionalesTest extends BaseTest {
  RepositorioReportesOrganizacionales repositorioReportesOrganizacionales = RepositorioReportesOrganizacionales.getInstance();
  @BeforeEach
  public void cleanRepo(){
    repositorioReportesOrganizacionales.limpiar();
  }
  @Test
  public void sePuedeObtenerUnReportePorId() {
    ReporteOrganizacional reporte = new ReporteOrganizacional(null,null,new HashMap<>());
    repositorioReportesOrganizacionales.agregar(reporte);
    assertEquals(reporte, repositorioReportesOrganizacionales.obtenerPorID(reporte.getId()).orElse(null));
  }

  @Test
  public void sePuedenObtenerTodosLosReportes() {
    ReporteOrganizacional reporte = new ReporteOrganizacional(null,null,new HashMap<>());
    ReporteOrganizacional otroReporte = new ReporteOrganizacional(null,null,new HashMap<>());
    repositorioReportesOrganizacionales.agregar(reporte);
    repositorioReportesOrganizacionales.agregar(otroReporte);

    assertEquals(asList(reporte,otroReporte), repositorioReportesOrganizacionales.obtenerTodos());
  }

  @Test
  public void sePuedeObtenerUnReporteSegunSuPeriodo( ) {
    Organizacion organizacion = crearOrganizacionVacia();
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteOrganizacional reporte = new ReporteOrganizacional(organizacion,periodo,new HashMap<>());

    repositorioReportesOrganizacionales.agregar(reporte);
    assertEquals(0.0, repositorioReportesOrganizacionales.HCTotalTipoDeOrganizacion(periodo, TipoDeOrganizacion.INSTITUCION));
  }
  @Test
  public void sePuedeObtenerLaEvolucionDeUnSector( ) {
    Organizacion organizacion = crearOrganizacionVacia();
    Periodo periodo = new Periodo(LocalDate.now(), Periodicidad.MENSUAL);
    ReporteOrganizacional reporte = new ReporteOrganizacional(organizacion,periodo,new HashMap<>());

    repositorioReportesOrganizacionales.agregar(reporte);
    assertEquals(asList(reporte), repositorioReportesOrganizacionales.evolucionHCTotalOrganizaccion(organizacion));
  }
}
