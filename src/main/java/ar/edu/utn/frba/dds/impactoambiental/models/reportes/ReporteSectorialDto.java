package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import static ar.edu.utn.frba.dds.impactoambiental.utils.MapUtil.merge;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ReporteSectorialDto {
  private Periodo periodo;
  private Double huellaCarbonoTotal;
  private Map<TipoDeConsumo, Double> huellaCarbonoPorTipoDeConsumo;
  private Map<TipoDeOrganizacion, Double> huellaCarbonoPorTipoDeOrganizacion;

  public ReporteSectorialDto(Periodo periodo,
                             Double huellaCarbonoTotal,
                             Map<TipoDeConsumo, Double> huellaCarbonoPorTipoDeConsumo,
                             Map<TipoDeOrganizacion, Double> huellaCarbonoPorTipoDeOrganizacion) {
    this.periodo = periodo;
    this.huellaCarbonoTotal = huellaCarbonoTotal;
    this.huellaCarbonoPorTipoDeConsumo = huellaCarbonoPorTipoDeConsumo;
    this.huellaCarbonoPorTipoDeOrganizacion = huellaCarbonoPorTipoDeOrganizacion;
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

  public Map<TipoDeOrganizacion, Double> getHuellaCarbonoPorTipoDeOrganizacion() {
    return huellaCarbonoPorTipoDeOrganizacion;
  }

  public ReporteSectorialDto compararCon(ReporteSectorialDto otroReporte) {
    return new ReporteSectorialDto(
        null,
        huellaCarbonoTotal - otroReporte.huellaCarbonoTotal,
        merge((a, b) -> a - b, huellaCarbonoPorTipoDeConsumo, otroReporte.huellaCarbonoPorTipoDeConsumo),
        merge((a, b) -> a - b, huellaCarbonoPorTipoDeOrganizacion, otroReporte.huellaCarbonoPorTipoDeOrganizacion)
    );
  }

  public static ReporteSectorialDto reporteVacio() {
    return new ReporteSectorialDto(null, null, ImmutableMap.of(), ImmutableMap.of());
  }
}
