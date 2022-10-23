package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Reporte {
  private List<TipoDeConsumo> tiposDeConsumo;
  private Periodo periodo;

  public Reporte(List<TipoDeConsumo> tiposDeConsumo, Periodo periodo) {
    this.tiposDeConsumo = tiposDeConsumo;
    this.periodo = periodo;
  }

  public List<TipoDeConsumo> getTiposDeConsumo() {
    return tiposDeConsumo;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public Double HCTotal() {
    return composicionHC().values().stream().mapToDouble(x -> x).sum();
  }

  public Map<TipoDeConsumo, Double> composicionHC() {
    return getTiposDeConsumo().stream()
        .collect(Collectors.toMap(tipoDeConsumo -> tipoDeConsumo, this::huellaCarbonoSegunConsumo));
  }

  public abstract Double huellaCarbonoSegunConsumo(TipoDeConsumo tipoDeConsumo);
}
