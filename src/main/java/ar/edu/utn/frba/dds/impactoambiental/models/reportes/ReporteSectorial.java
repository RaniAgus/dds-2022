package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import java.util.List;

public class ReporteSectorial extends Reporte {
  private SectorTerritorial sectorTerritorial;

  public ReporteSectorial(List<TipoDeConsumo> tiposDeConsumo, Periodo periodo, SectorTerritorial sectorTerritorial) {
    super(tiposDeConsumo, periodo);
    this.sectorTerritorial = sectorTerritorial;
  }

  public SectorTerritorial getSectorTerritorial() {
    return sectorTerritorial;
  }

  @Override
  public Double huellaCarbonoSegunConsumo(TipoDeConsumo tipoDeConsumo) {
    return sectorTerritorial.huellaCarbonoSegunConsumo(getPeriodo(), tipoDeConsumo);
  }
}
