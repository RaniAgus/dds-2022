package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import java.util.List;

public class ReporteOrganizacional extends Reporte {
  private Organizacion organizacion;

  public ReporteOrganizacional(List<TipoDeConsumo> tiposDeConsumo, Periodo periodo, Organizacion organizacion) {
    super(tiposDeConsumo, periodo);
    this.organizacion = organizacion;
  }

  public Organizacion getOrganizacion() {
    return organizacion;
  }

  public Double huellaCarbonoSegunConsumo(TipoDeConsumo tipoDeConsumo) {
    return getOrganizacion().huellaCarbonoSegunConsumo(getPeriodo(), tipoDeConsumo);
  }
}
