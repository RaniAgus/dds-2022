package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import static ar.edu.utn.frba.dds.impactoambiental.utils.MapUtil.merge;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ReporteOrganizacionalDto {
  private Periodo periodo;
  private Double huellaCarbonoTotal;
  private Map<TipoDeConsumo, Double> huellaCarbonoPorTipoDeConsumo;

  public ReporteOrganizacionalDto(Periodo periodo,
                                  Double huellaCarbonoTotal,
                                  Map<TipoDeConsumo, Double> huellaCarbonoPorTipoDeConsumo) {
    this.periodo = periodo;
    this.huellaCarbonoTotal = huellaCarbonoTotal;
    this.huellaCarbonoPorTipoDeConsumo = huellaCarbonoPorTipoDeConsumo;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public Double getHuellaCarbonoTotal() {
    return huellaCarbonoTotal;
  }

  public Map<TipoDeConsumo, Double> getHuellaCarbonoPorTipoDeConsumo() {
    return huellaCarbonoPorTipoDeConsumo;
  }

  public ReporteOrganizacionalDto compararCon(ReporteOrganizacionalDto otroReporte) {
    return new ReporteOrganizacionalDto(
        null,
        huellaCarbonoTotal - otroReporte.huellaCarbonoTotal,
        merge((a, b) -> a - b, huellaCarbonoPorTipoDeConsumo, otroReporte.huellaCarbonoPorTipoDeConsumo)
    );
  }

  public static ReporteOrganizacionalDto reporteVacio() {
    return new ReporteOrganizacionalDto(null, null, ImmutableMap.of());
  }
}
