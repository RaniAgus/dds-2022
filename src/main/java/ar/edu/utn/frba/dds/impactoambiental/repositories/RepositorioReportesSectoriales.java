package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteSectorial;
import java.util.List;
import java.util.Optional;

public final class RepositorioReportesSectoriales implements Repositorio<ReporteSectorial> {
  private static final RepositorioReportesSectoriales instance = new RepositorioReportesSectoriales();

  public static RepositorioReportesSectoriales getInstance() {
    return instance;
  }

  public List<ReporteSectorial> evolucionHCTotalSector(SectorTerritorial sector) {
    return filtrar("sectorTerritorial.id", sector.getId());
  }

  public Optional<ReporteSectorial> reporteSectorialSegunPeriodo(Periodo periodo, SectorTerritorial sector) {
    return buscar(
        "periodo.inicioPeriodo", periodo.getInicioPeriodo(),
        "periodo.periodicidad", periodo.getPeriodicidad(),
        "sectorTerritorial.id", sector.getId()
    );
  }

  @Override
  public Class<ReporteSectorial> clase() {
    return ReporteSectorial.class;
  }
}
