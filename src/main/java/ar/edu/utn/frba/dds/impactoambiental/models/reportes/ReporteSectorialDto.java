package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import java.util.Map;
import java.util.stream.Collectors;

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
        huellaCarbonoPorTipoDeConsumo.entrySet().stream().collect(
            Collectors.toMap(Map.Entry::getKey,
                e -> e.getValue() - otroReporte.huellaCarbonoPorTipoDeConsumo.get(e.getKey()))),
        huellaCarbonoPorTipoDeOrganizacion
    );
  }
}
