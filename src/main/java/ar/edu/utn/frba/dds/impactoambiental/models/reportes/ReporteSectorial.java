package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReporteSectorial extends Reporte {
  private SectorTerritorial sectorTerritorial;

  public ReporteSectorial(List<TipoDeConsumo> tiposDeConsumo, Periodo periodo, SectorTerritorial sectorTerritorial) {
    super(tiposDeConsumo, periodo);
    this.sectorTerritorial = sectorTerritorial;
  }

  public SectorTerritorial getSectorTerritorial() {
    return sectorTerritorial;
  }

  public Map<TipoDeOrganizacion, Double> getHuellaCarbonoTotalPorTipoDeOrganizacion() {
    return Stream.of(TipoDeOrganizacion.values()).collect(Collectors.toMap(
        tipo -> tipo, tipo -> sectorTerritorial.huellaCarbonoSegunTipoDeOrganizacion(getPeriodo(), tipo)));
  }

  @Override
  public Double huellaCarbonoSegunConsumo(TipoDeConsumo tipoDeConsumo) {
    return getSectorTerritorial().huellaCarbonoSegunConsumo(getPeriodo(), tipoDeConsumo);
  }
}
