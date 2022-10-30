package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import java.util.List;

public class ReporteOrganizacionalFactory extends ReporteFactory {
  private Organizacion organizacion;

  public ReporteOrganizacionalFactory(List<TipoDeConsumo> tiposDeConsumo,
                                      Periodo periodo,
                                      Organizacion organizacion) {
    super(tiposDeConsumo, periodo);
    this.organizacion = organizacion;
  }

  private Organizacion getOrganizacion() {
    return organizacion;
  }

  @Override
  protected Double getHuellaCarbonoPara(TipoDeConsumo tipoDeConsumo) {
    return getOrganizacion().huellaCarbonoSegunConsumo(getPeriodo(), tipoDeConsumo);
  }

  public ReporteOrganizacionalDto getReporte() {
    return new ReporteOrganizacionalDto(
        getPeriodo(),
        getHuellaCarbonoTotal(),
        getHuellaCarbonoPorTipoConsumo());
  }
}
