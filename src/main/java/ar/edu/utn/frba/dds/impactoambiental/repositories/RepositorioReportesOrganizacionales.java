package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.Reporte;
import ar.edu.utn.frba.dds.impactoambiental.models.reportes.ReporteOrganizacional;
import java.util.List;

public final class RepositorioReportesOrganizacionales implements Repositorio<ReporteOrganizacional> {
  private static final RepositorioReportesOrganizacionales instance = new RepositorioReportesOrganizacionales();

  private RepositorioReportesOrganizacionales() {
    super();
  }

  public static RepositorioReportesOrganizacionales getInstance() {
    return instance;
  }

  public Double HCTotalTipoDeOrganizacion(Periodo periodo, TipoDeOrganizacion tipoDeOrganizacion) {
    return filtrar(
        "periodo.periodicidad", periodo.getPeriodicidad(),
        "periodo.inicioPeriodo", periodo.getInicioPeriodo(),
        "organizacion.tipo", tipoDeOrganizacion
    ).stream().mapToDouble(Reporte::HCTotal).sum();
  }

  // Entre dos períodos o dos fechas con una periodicidad
  public List<ReporteOrganizacional> evolucionHCTotalOrganizaccion(Organizacion org) {
    return filtrar("organizacion.id", org.getId());
  }

  public Class<ReporteOrganizacional> clase() {
    return ReporteOrganizacional.class;
  }
}
