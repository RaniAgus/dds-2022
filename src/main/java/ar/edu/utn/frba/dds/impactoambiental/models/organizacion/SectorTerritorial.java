package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
@Entity
public class SectorTerritorial {
  @Id
  @GeneratedValue
  private long id;
  private String nombre;
  @OneToMany
  private List<Organizacion> organizaciones;

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
}
