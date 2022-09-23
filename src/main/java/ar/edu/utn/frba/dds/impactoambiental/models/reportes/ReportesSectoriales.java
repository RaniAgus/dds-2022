package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TiposDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ReportesSectoriales implements Repositorio<ReporteSectorial> {
  private static final ReportesSectoriales instance = new ReportesSectoriales();

  public static ReportesSectoriales getInstance() {
    return instance;
  }


  public List<ReporteSectorial> evolucionHCTotalSector(SectorTerritorial sector) {
    return entityManager().createQuery("SELECT reporte FROM ReporteSectorial reporte WHERE reporte.sectorTerritorial.id = :id", ReporteSectorial.class)
        .setParameter("id", sector.getId())
        .getResultList();
  }

  public Optional<ReporteSectorial> reporteSectorialSegunPeriodo(Periodo periodo, SectorTerritorial sector) {
    return entityManager().createQuery("SELECT reporte FROM ReporteSectorial reporte WHERE reporte.periodo.inicioPeriodo = :inicioPeriodo AND reporte.periodo.periodicidad = :periodicidad AND reporte.sectorTerritorial.id = :id", ReporteSectorial.class)
        .setParameter("periodicidad", periodo.getPeriodicidad()).setParameter("inicioPeriodo", periodo.getInicioPeriodo()).setParameter("id", sector.getId())
        .getResultList().stream().findFirst();
  }

  @Override
  public Class<ReporteSectorial> clase() {
    return ReporteSectorial.class;
  }
}