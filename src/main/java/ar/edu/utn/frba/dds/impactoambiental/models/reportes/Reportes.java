package ar.edu.utn.frba.dds.impactoambiental.models.reportes;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TiposDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Reportes implements Repositorio<Reporte> {
    private static final Reportes instance = new Reportes();

    public static Reportes getInstance() {
        return instance;
    }

    public Optional<Double> HCTotalSector(Periodo periodo, SectorTerritorial sectorTerritorial) {
        return reporteSectorialSegunPeriodo(periodo, sectorTerritorial).map(Reporte::HCTotal);
    }

    public Double HCTotalTipoDeOrganizacion(Periodo periodo, TipoDeOrganizacion tipoDeOrganizacion) {
        return entityManager().createQuery("SELECT reporte FROM ReporteOrganizacional reporte WHERE reporte.inicioPeriodo = :inicioPeriodo AND reporte.periodocidad = :periodicidad AND reporte.organizacion.tipo = :tipo", ReporteOrganizacional.class)
                .setParameter("periodicidad", periodo.getPeriodicidad()).setParameter("inicioPeriodo", periodo.getInicioPeriodo()).setParameter("tipo", tipoDeOrganizacion)
                .getResultList().stream().mapToDouble(Reporte::HCTotal).sum();
    }

    public Optional<Map<TipoDeConsumo, Double>> composicionHCSector(Periodo periodo, SectorTerritorial sector) {
        return reporteSectorialSegunPeriodo(periodo, sector).map(Reporte::composicionHC);
    }

    public Optional<Map<TipoDeConsumo, Double>> composicionHCOrganizacion(Periodo periodo, Organizacion org) {
        Optional<ReporteOrganizacional> reporte = entityManager().createQuery("SELECT reporte FROM ReporteOrganizacional reporte WHERE reporte.inicioPeriodo = :inicioPeriodo AND reporte.periodocidad = :periodicidad AND reporte.organizacion.id = :id", ReporteOrganizacional.class)
                .setParameter("periodicidad", periodo.getPeriodicidad()).setParameter("inicioPeriodo", periodo.getInicioPeriodo()).setParameter("id", org.getId())
                .getResultList().stream().findFirst();
        return reporte.map(Reporte::composicionHC);    }

    public List<ReporteOrganizacional> evolucionHCTotalOrganizaccion(Organizacion org) {
        return entityManager().createQuery("SELECT reporte FROM ReporteOrganizacional reporte WHERE reporte.organizacion_id = :id", ReporteOrganizacional.class)
                .setParameter("id", org.getId())
                .getResultList();
    }

    public List<ReporteSectorial> evolucionHCTotalSector(SectorTerritorial sector) {
        return entityManager().createQuery("SELECT reporte FROM ReporteSectorial reporte WHERE reporte.sectorTerritorial_id = :id", ReporteSectorial.class)
                .setParameter("id", sector.getId())
                .getResultList();
    }

    public ReporteOrganizacional generarReporteOrganizacional(Organizacion organizacion, Periodo periodo, TiposDeConsumo repoTiposDeConsumo) {
        Map<TipoDeConsumo, Double> consumos = new HashMap<>();
        repoTiposDeConsumo.obtenerTodos()
                .forEach(tipoDeConsumo -> {consumos.put(tipoDeConsumo, organizacion.huellaCarbonoSegunConsumo(periodo, tipoDeConsumo));});
        ReporteOrganizacional nuevoReporte = new ReporteOrganizacional(organizacion, periodo, consumos);
        entityManager().persist(nuevoReporte);
        return nuevoReporte;
    }

    public ReporteSectorial generarReporteSectorial(SectorTerritorial sectorTerritorial, Periodo periodo, TiposDeConsumo repoTiposDeConsumo) {
        HashMap<TipoDeConsumo, Double> consumos = new HashMap<>();
        repoTiposDeConsumo.obtenerTodos()
                .forEach(tipoDeConsumo -> {consumos.put(tipoDeConsumo, sectorTerritorial.huellaCarbonoSegunConsumo(periodo, tipoDeConsumo));});
        ReporteSectorial nuevoReporte = new ReporteSectorial(sectorTerritorial, periodo, consumos);
        entityManager().persist(nuevoReporte);
        return nuevoReporte;
    }
    public Optional<ReporteSectorial> reporteSectorialSegunPeriodo(Periodo periodo, SectorTerritorial sector) {
        return entityManager().createQuery("SELECT reporte FROM ReporteSectorial reporte WHERE reporte.inicioPeriodo = :inicioPeriodo AND reporte.periodocidad = :periodicidad AND reporte.sectorTerritorial_id = :id", ReporteSectorial.class)
                .setParameter("periodicidad", periodo.getPeriodicidad()).setParameter("inicioPeriodo", periodo.getInicioPeriodo()).setParameter("id", sector.getId())
                .getResultList().stream().findFirst();
    }

    @Override
    public Class<Reporte> clase() {
        return Reporte.class;
    }
}