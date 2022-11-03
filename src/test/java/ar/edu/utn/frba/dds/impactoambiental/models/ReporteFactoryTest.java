package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacionalDto;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacionalFactory;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorialDto;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorialFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReporteFactoryTest extends BaseTest {
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
    ReporteSectorialDto reporte = new ReporteSectorialFactory(
        repositorioTipoDeConsumo.obtenerTodos(), periodoAnual, sectorTerritorial).getReporte();

    assertThat(reporte.getHuellaCarbonoPorTipoDeConsumo())
        .containsEntry(nafta, 100.0)
        .containsEntry(electricidad, 200.0);
  }

  @Test
  public void sePuedeObtenerHCTotal() {
    ReporteOrganizacionalDto reporte = new ReporteOrganizacionalFactory(
        repositorioTipoDeConsumo.obtenerTodos(), periodoAnual, organizacion).getReporte();

    assertThat(reporte.getHuellaCarbonoTotal()).isEqualTo(700.0);
  }

  @Test
  public void sePuedeObtenerHCTotalPorTipoDeOrganizacion() {
    ReporteSectorialDto reporte = new ReporteSectorialFactory(
        repositorioTipoDeConsumo.obtenerTodos(), periodoAnual, sectorTerritorial).getReporte();

    assertThat(reporte.getHuellaCarbonoPorTipoDeOrganizacion())
        .containsEntry(TipoDeOrganizacion.EMPRESA, 100.0)
        .containsEntry(TipoDeOrganizacion.ONG, 200.0);
  }

}
