package ar.edu.utn.frba.dds.impactoambiental.dtos;

public class SidebarOrganizacion {
  private boolean vinculaciones;
  private boolean datosActividad;
  private boolean reportesEvolucion;
  private boolean reportesIndividual;
  
  public SidebarOrganizacion(boolean vinculaciones, boolean datosActividad, boolean reportesEvolucion, boolean reportesIndividual) {
    this.vinculaciones = vinculaciones;
    this.datosActividad = datosActividad;
    this.reportesEvolucion = reportesEvolucion;
    this.reportesIndividual = reportesIndividual;
  }

  public boolean getVinculaciones(){
    return vinculaciones;
  }

  public boolean getDatosActividad(){
    return datosActividad;
  }

  public boolean getReportesEvolucion(){
    return reportesEvolucion;
  }

  public boolean getReportesIndividual(){
    return reportesIndividual;
  }
}
