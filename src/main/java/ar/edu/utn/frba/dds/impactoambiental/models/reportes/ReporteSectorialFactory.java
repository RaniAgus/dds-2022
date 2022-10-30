package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteSectorialFactory extends ReporteFactory {
  private SectorTerritorial sectorTerritorial;

  public ReporteSectorialFactory(List<TipoDeConsumo> tiposDeConsumo,
                                 Periodo periodo,
                                 SectorTerritorial sectorTerritorial) {
    super(tiposDeConsumo, periodo);
    this.sectorTerritorial = sectorTerritorial;
  }

  private SectorTerritorial getSectorTerritorial() {
    return sectorTerritorial;
  }

  private Map<TipoDeOrganizacion, Double> getHuellaCarbonoTotalPorTipoDeOrganizacion() {
    return Arrays.stream(TipoDeOrganizacion.values()).collect(Collectors.toMap(
        tipo -> tipo, tipo -> sectorTerritorial.huellaCarbonoSegunTipoDeOrganizacion(getPeriodo(), tipo)));
  }

  @Override
  protected Double getHuellaCarbonoPara(TipoDeConsumo tipoDeConsumo) {
    return getSectorTerritorial().huellaCarbonoSegunConsumo(getPeriodo(), tipoDeConsumo);
  }

  public ReporteSectorialDto getReporte() {
    return new ReporteSectorialDto(
        getPeriodo(),
        getHuellaCarbonoTotal(),
        getHuellaCarbonoPorTipoConsumo(),
        getHuellaCarbonoTotalPorTipoDeOrganizacion());
  }
}
