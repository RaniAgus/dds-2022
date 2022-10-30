package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import java.util.Map;

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
}
