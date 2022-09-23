package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Map;

@Entity
public class ReporteOrganizacional extends Reporte {

    @ManyToOne
    Organizacion organizacion;

    public ReporteOrganizacional(Organizacion organizacion, Periodo periodo, Map<TipoDeConsumo, Double> consumos) {
        this.organizacion = organizacion;
        this.periodo = periodo;
        this.consumos = consumos;
    }

}
