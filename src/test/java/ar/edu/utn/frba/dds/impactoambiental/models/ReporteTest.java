package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacional;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReporteTest extends BaseTest {
  private SectorTerritorial sectorTerritorial;
  private Organizacion organizacion;

  @BeforeEach
  public void setUp() {
    super.setUp();
    sectorTerritorial = mock(SectorTerritorial.class);
    when(sectorTerritorial.huellaCarbonoSegunConsumo(periodoAnual, nafta)).thenReturn(100.0);
    when(sectorTerritorial.huellaCarbonoSegunConsumo(periodoAnual, electricidad)).thenReturn(200.0);
    when(sectorTerritorial.huellaCarbonoSegunTipoDeOrganizacion(periodoAnual, TipoDeOrganizacion.EMPRESA)).thenReturn(100.0);
    when(sectorTerritorial.huellaCarbonoSegunTipoDeOrganizacion(periodoAnual, TipoDeOrganizacion.ONG)).thenReturn(200.0);
    organizacion = mock(Organizacion.class);
    when(organizacion.huellaCarbonoSegunConsumo(periodoAnual, nafta)).thenReturn(300.0);
    when(organizacion.huellaCarbonoSegunConsumo(periodoAnual, electricidad)).thenReturn(400.0);
  }

  @Test
  public void sePuedeObtenerLaComposicionHC() {
    ReporteSectorial reporte = new ReporteSectorial(repositorioTipoDeConsumo.obtenerTodos(), periodoAnual, sectorTerritorial);
    assertThat(reporte.getComposicionHuellaCarbono())
        .containsEntry(nafta, 100.0)
        .containsEntry(electricidad, 200.0);
  }

  @Test
  public void sePuedeObtenerHCTotal() {
    ReporteOrganizacional reporte = new ReporteOrganizacional(repositorioTipoDeConsumo.obtenerTodos(), periodoAnual, organizacion);
    assertThat(reporte.getHuellaCarbonoTotal()).isEqualTo(700.0);
  }

  @Test
  public void sePuedeObtenerHCTotalPorTipoDeOrganizacion() {
    ReporteSectorial reporte = new ReporteSectorial(repositorioTipoDeConsumo.obtenerTodos(), periodoAnual, sectorTerritorial);
    assertThat(reporte.getHuellaCarbonoTotalPorTipoDeOrganizacion())
        .containsEntry(TipoDeOrganizacion.EMPRESA, 100.0)
        .containsEntry(TipoDeOrganizacion.ONG, 200.0);
  }

}
