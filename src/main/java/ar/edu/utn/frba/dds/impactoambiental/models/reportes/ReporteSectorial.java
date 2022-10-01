package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.RepositorioTipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.HashMap;

@Entity
public class ReporteSectorial extends Reporte {

  @ManyToOne(cascade= CascadeType.ALL)
  SectorTerritorial sectorTerritorial;

  public ReporteSectorial(SectorTerritorial sectorTerritorial, Periodo periodo, HashMap<TipoDeConsumo, Double> consumos) {
    this.sectorTerritorial = sectorTerritorial;
    this.periodo = periodo;
    this.consumos = consumos;
  }
  public ReporteSectorial(SectorTerritorial sectorTerritorial, Periodo periodo, RepositorioTipoDeConsumo repoRepositorioTipoDeConsumo) {
    HashMap<TipoDeConsumo, Double> consumos = new HashMap<>();
    repoRepositorioTipoDeConsumo.obtenerTodos()
        .forEach(tipoDeConsumo -> {
          consumos.put(tipoDeConsumo, sectorTerritorial.huellaCarbonoSegunConsumo(periodo, tipoDeConsumo));
        });
    ReporteSectorial nuevoReporte = new ReporteSectorial(sectorTerritorial, periodo, consumos);
  }
}
