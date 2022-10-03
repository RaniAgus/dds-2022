package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import com.google.common.collect.ImmutableMap;

import java.util.List;

public final class ReportesOrganizacionales implements Repositorio<ReporteOrganizacional> {
  private static final ReportesOrganizacionales instance = new ReportesOrganizacionales();

  private ReportesOrganizacionales() {
  }

  public static ReportesOrganizacionales getInstance() {
    return instance;
  }

  public Double HCTotalTipoDeOrganizacion(Periodo periodo, TipoDeOrganizacion tipoDeOrganizacion) {
    return filtrarPorAtributos(ImmutableMap.of(
        "periodo.periodicidad", periodo.getPeriodicidad(),
        "periodo.inicioPeriodo", periodo.getInicioPeriodo(),
        "organizacion.tipo", tipoDeOrganizacion
    )).stream().mapToDouble(Reporte::HCTotal).sum();
  }

  // Entre dos períodos o dos fechas con una periodicidad
  public List<ReporteOrganizacional> evolucionHCTotalOrganizaccion(Organizacion org) {
    return filtrarPorAtributo("organizacion.id", org.getId());
  }

  public Class<ReporteOrganizacional> clase() {
    return ReporteOrganizacional.class;
  }
}
