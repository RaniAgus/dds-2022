package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioTipoDeConsumo;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReporteOrganizacional extends Reporte {

  @ManyToOne(cascade= CascadeType.ALL)
  private Organizacion organizacion;

  protected ReporteOrganizacional() {}

  public ReporteOrganizacional(Organizacion organizacion, Periodo periodo, Map<TipoDeConsumo, Double> consumos) {
    this.organizacion = organizacion;
    this.periodo = periodo;
    this.consumos = consumos;
  }

  public Organizacion getOrganizacion() {
    return organizacion;
  }

  public TipoDeOrganizacion getTipoDeOrganizacion() {
    return organizacion.getTipo();
  }

  public ReporteOrganizacional(Organizacion organizacion, Periodo periodo, RepositorioTipoDeConsumo repoRepositorioTipoDeConsumo) {
    Map<TipoDeConsumo, Double> consumos = new HashMap<>();
    repoRepositorioTipoDeConsumo.obtenerTodos()
        .forEach(tipoDeConsumo -> {
          consumos.put(tipoDeConsumo, organizacion.huellaCarbonoSegunConsumo(periodo, tipoDeConsumo));
        });
    ReporteOrganizacional nuevoReporte = new ReporteOrganizacional(organizacion, periodo, consumos);
  }

}
