package ar.edu.utn.frba.dds.impactoambiental.dtos;

public class SidebarSector {
  private boolean reporteOrganizacionalIndividual;
  private boolean reporteOrganizacionalEvolucion;
  private boolean reporteConsumoIndividual;
  private boolean reporteConsumoEvolucion;

  public SidebarSector(boolean reporteOrganizacionalIndividual, boolean reporteOrganizacionalEvolucion, boolean reporteConsumoIndividual, boolean reporteConsumoEvolucion) {
    this.reporteOrganizacionalIndividual = reporteOrganizacionalIndividual;
    this.reporteOrganizacionalEvolucion = reporteOrganizacionalEvolucion;
    this.reporteConsumoIndividual = reporteConsumoIndividual;
    this.reporteConsumoEvolucion = reporteConsumoEvolucion;
  }

  public boolean getReporteOrganizacionalIndividual() {
    return reporteOrganizacionalIndividual;
  }

  public boolean getReporteOrganizacionalEvolucion() {
    return reporteOrganizacionalEvolucion;
  }

  public boolean getReporteConsumoIndividual() {
    return reporteConsumoIndividual;
  }

  public boolean getReporteConsumoEvolucion() {
    return reporteConsumoEvolucion;
  }
}
