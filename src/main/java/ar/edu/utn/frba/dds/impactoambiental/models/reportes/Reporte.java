package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Reporte extends EntidadPersistente {
  @Embedded
  protected Periodo periodo;

  @ElementCollection
  @MapKeyJoinColumn(name = "tipodeconsumo_id")
  protected Map<TipoDeConsumo, Double> consumos;

  public Periodo getPeriodo() {
    return periodo;
  }

  public Double HCTotal() {
    return consumos.values().stream().mapToDouble(x -> x).sum();
  }

  public Map<TipoDeConsumo, Double> composicionHC() {
    return consumos;
  }
}
