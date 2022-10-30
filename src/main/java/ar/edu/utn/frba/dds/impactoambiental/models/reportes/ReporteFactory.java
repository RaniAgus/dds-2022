package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ReporteFactory {
  private List<TipoDeConsumo> tiposDeConsumo;
  private Periodo periodo;

  public ReporteFactory(List<TipoDeConsumo> tiposDeConsumo, Periodo periodo) {
    this.tiposDeConsumo = tiposDeConsumo;
    this.periodo = periodo;
  }

  protected Double getHuellaCarbonoTotal() {
    return getHuellaCarbonoPorTipoConsumo().values().stream().mapToDouble(x -> x).sum();
  }

  protected Map<TipoDeConsumo, Double> getHuellaCarbonoPorTipoConsumo() {
    return getTiposDeConsumo().stream()
        .collect(Collectors.toMap(tipoDeConsumo -> tipoDeConsumo, this::getHuellaCarbonoPara));
  }

  protected List<TipoDeConsumo> getTiposDeConsumo() {
    return tiposDeConsumo;
  }

  protected Periodo getPeriodo() {
    return periodo;
  }

  protected abstract Double getHuellaCarbonoPara(TipoDeConsumo tipoDeConsumo);
}
