package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class SectorTerritorial extends EntidadPersistente {
  private String nombre;
  @OneToMany
  @JoinColumn(name = "sector_territorial_id")
  private List<Organizacion> organizaciones;

  protected SectorTerritorial() {
  }

  public SectorTerritorial(String nombre, List<Organizacion> organizaciones) {
    this.nombre = nombre;
    this.organizaciones = organizaciones;
  }

  public void agregarOrganizacion(Organizacion organizacion) {
    organizaciones.add(organizacion);
  }

  public Double huellaCarbono(Periodo periodo) {
    return organizaciones.stream()
        .mapToDouble(o -> o.huellaCarbono(periodo))
        .sum();
  }

  public Double huellaCarbonoSegunConsumo(Periodo periodo, TipoDeConsumo tipoDeConsumo) {
    return organizaciones.stream()
        .mapToDouble(o -> o.huellaCarbonoSegunConsumo(periodo, tipoDeConsumo))
        .sum();
  }

  public Double huellaCarbonoSegunTipoDeOrganizacion(Periodo periodo, TipoDeOrganizacion tipoDeOrganizacion) {
    return organizaciones.stream()
        .filter(o -> o.getTipo() == tipoDeOrganizacion)
        .mapToDouble(o -> o.huellaCarbono(periodo))
        .sum();
  }

  public List<Integer> aniosConsumo() {
    return organizaciones.stream().flatMap(o -> o.aniosConsumo().stream())
        .distinct()
        .sorted()
        .collect(Collectors.toList());
  }
}
