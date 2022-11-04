package ar.edu.utn.frba.dds.impactoambiental.dtos;

public class FilaReporteEvolucionDto {
  Double primero;
  Double segundo;
  Double evolucion;

  public FilaReporteEvolucionDto(Double primero, Double segundo, Double evolucion) {
    this.primero = primero;
    this.segundo = segundo;
    this.evolucion = evolucion;
  }

  public Double getPrimero() {
    return primero;
  }

  public Double getSegundo() {
    return segundo;
  }

  public Double getEvolucion() {
    return evolucion;
  }
}
