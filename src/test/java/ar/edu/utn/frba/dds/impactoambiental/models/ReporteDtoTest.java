package ar.edu.utn.frba.dds.impactoambiental.models;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacionalDto;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorialDto;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

public class ReporteDtoTest extends BaseTest {

  @Test
  public void sePuedenCompararDosReportesSectoriales() {
    ReporteSectorialDto reporte1 = new ReporteSectorialDto(
        periodoAnual, 300.0,
        ImmutableMap.of(nafta, 200.0, electricidad, 100.0),
        ImmutableMap.of(TipoDeOrganizacion.EMPRESA, 100.0, TipoDeOrganizacion.ONG, 200.0));

    ReporteSectorialDto reporte2 = new ReporteSectorialDto(
        periodoAnual, 200.0,
        ImmutableMap.of(nafta, 50.0, electricidad, 150.0),
        ImmutableMap.of(TipoDeOrganizacion.EMPRESA, 50.0, TipoDeOrganizacion.ONG, 100.0));

    ReporteSectorialDto comparacion = reporte1.compararCon(reporte2);

    assertThat(comparacion.getHuellaCarbonoTotal()).isEqualTo(100.0);
    assertThat(comparacion.getHuellaCarbonoPorTipoDeConsumo())
        .containsEntry(nafta, 150.0)
        .containsEntry(electricidad, -50.0);
    assertThat(comparacion.getHuellaCarbonoPorTipoDeOrganizacion())
        .containsEntry(TipoDeOrganizacion.EMPRESA, 50.0)
        .containsEntry(TipoDeOrganizacion.ONG, 100.0);
  }

  @Test
  public void sePuedenCompararDosReportesOrganizacionales() {
    ReporteOrganizacionalDto reporte1 = new ReporteOrganizacionalDto(
        periodoAnual, 300.0,
        ImmutableMap.of(nafta, 200.0, electricidad, 100.0));

    ReporteOrganizacionalDto reporte2 = new ReporteOrganizacionalDto(
        periodoAnual, 200.0,
        ImmutableMap.of(nafta, 50.0, electricidad, 150.0));

    ReporteOrganizacionalDto comparacion = reporte1.compararCon(reporte2);

    assertThat(comparacion.getHuellaCarbonoTotal()).isEqualTo(100.0);
    assertThat(comparacion.getHuellaCarbonoPorTipoDeConsumo())
        .containsEntry(nafta, 150.0)
        .containsEntry(electricidad, -50.0);
  }
}
