package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;

import java.util.List;
import java.util.stream.Collectors;

public final class RepositorioReportesOrganizacionales extends Repositorio<ReporteOrganizacional> {
  private static final RepositorioReportesOrganizacionales instance = new RepositorioReportesOrganizacionales();

  private RepositorioReportesOrganizacionales() {
    super();
  }

  public static RepositorioReportesOrganizacionales getInstance() {
    return instance;
  }

  public Double HCTotalTipoDeOrganizacion(Periodo periodo, TipoDeOrganizacion tipoDeOrganizacion) {
    return repositorio.stream()
        .filter(reporte ->reporte.periodo.equals(periodo)
            && reporte.organizacion.getTipo().equals(tipoDeOrganizacion))
        .mapToDouble(Reporte::HCTotal)
        .sum();
  }

  // Entre dos períodos o dos fechas con una periodicidad
  public List<ReporteOrganizacional> evolucionHCTotalOrganizaccion(Organizacion org) {
    return repositorio.stream()
        .filter(reporte -> reporte.organizacion.equals(org))
        .collect(Collectors.toList());
  }

  public Class<ReporteOrganizacional> clase() {
    return ReporteOrganizacional.class;
  }
}
