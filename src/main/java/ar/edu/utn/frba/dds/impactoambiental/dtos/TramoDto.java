package ar.edu.utn.frba.dds.impactoambiental.dtos;

public class TramoDto {
  String tipo;
  String medio;
  String origen;
  String destino;

  public TramoDto(String tipo, String medio, String origen, String destino) {
    this.tipo = tipo;
    this.medio = medio;
    this.origen = origen;
    this.destino = destino;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getMedio() {
    return medio;
  }

  public void setMedio(String medio) {
    this.medio = medio;
  }

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public String getDestino() {
    return destino;
  }

  public void setDestino(String destino) {
    this.destino = destino;
  }
}
