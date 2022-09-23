package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MappedSuperclass;
import java.util.Map;

@MappedSuperclass
public abstract class Reporte extends EntidadPersistente {
  @Embedded
  Periodo periodo;

  @ElementCollection
  @MapKeyJoinColumn(name = "tipodeconsumo_id")
  Map<TipoDeConsumo, Double> consumos;

  public Double HCTotal() {
    return consumos.values().stream().mapToDouble(x -> x).sum();
  }

  public Map<TipoDeConsumo, Double> composicionHC() {
    return consumos;
  }
}
