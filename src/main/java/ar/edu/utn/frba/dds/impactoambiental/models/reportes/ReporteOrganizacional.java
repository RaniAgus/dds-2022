package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.RepositorioTipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ReporteOrganizacional extends Reporte {

  @ManyToOne(cascade= CascadeType.ALL)
  Organizacion organizacion;

  public ReporteOrganizacional(Organizacion organizacion, Periodo periodo, Map<TipoDeConsumo, Double> consumos) {
    this.organizacion = organizacion;
    this.periodo = periodo;
    this.consumos = consumos;
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