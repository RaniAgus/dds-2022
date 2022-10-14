package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RepositorioReportesSectoriales extends Repositorio<ReporteSectorial> {
  private static final RepositorioReportesSectoriales instance = new RepositorioReportesSectoriales();

  public static RepositorioReportesSectoriales getInstance() {
    return instance;
  }

  public List<ReporteSectorial> evolucionHCTotalSector(SectorTerritorial sector) {
    return repositorio.stream()
        .filter(reporte -> reporte.sectorTerritorial.equals(sector))
        .collect(Collectors.toList());
  }

  public Optional<ReporteSectorial> reporteSectorialSegunPeriodo(Periodo periodo, SectorTerritorial sector) {
    return repositorio.stream()
        .filter(reporte -> reporte.sectorTerritorial.equals(sector)
            && reporte.periodo.equals(periodo)).findFirst();
  }

  @Override
  public Class<ReporteSectorial> clase() {
    return ReporteSectorial.class;
  }
}
