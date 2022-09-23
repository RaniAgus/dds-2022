package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TiposDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReportesOrganizacionales implements Repositorio<ReporteOrganizacional> {
  private static final ReportesOrganizacionales instance = new ReportesOrganizacionales();

  private ReportesOrganizacionales() {
  }

  public static ReportesOrganizacionales getInstance() {
    return instance;
  }

  public Double HCTotalTipoDeOrganizacion(Periodo periodo, TipoDeOrganizacion tipoDeOrganizacion) {
    return entityManager().createQuery("SELECT reporte FROM ReporteOrganizacional reporte WHERE reporte.periodo.inicioPeriodo = :inicioPeriodo AND reporte.periodo.periodicidad = :periodicidad AND reporte.organizacion.tipo = :tipo", ReporteOrganizacional.class)
        .setParameter("periodicidad", periodo.getPeriodicidad()).setParameter("inicioPeriodo", periodo.getInicioPeriodo()).setParameter("tipo", tipoDeOrganizacion)
        .getResultList().stream().mapToDouble(Reporte::HCTotal).sum();
  }

  public List<ReporteOrganizacional> evolucionHCTotalOrganizaccion(Organizacion org) {
    return entityManager().createQuery("SELECT reporte FROM ReporteOrganizacional reporte WHERE reporte.organizacion.id = :id", ReporteOrganizacional.class)
        .setParameter("id", org.getId())
        .getResultList();
  }

  public Class<ReporteOrganizacional> clase() {
    return ReporteOrganizacional.class;
  }
}
