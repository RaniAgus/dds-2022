package ar.edu.utn.frba.dds.impactoambiental.dtos;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Tramo;
import java.util.List;
import java.util.stream.Collectors;

public class TramoDto {
  private String tipo;
  private String medio;
  private String origen;
  private String destino;

  public TramoDto(String tipo, String medio, String origen, String destino) {
    this.tipo = tipo;
    this.medio = medio;
    this.origen = origen;
    this.destino = destino;
  }

  public TramoDto(Tramo tramo) {
    this(tramo.tipo(), tramo.nombreMedio(), tramo.nombreOrigen(), tramo.nombreDestino());
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

  public static List<TramoDto> ofList(List<Tramo> tramos) {
    return tramos.stream().map(TramoDto::new).collect(Collectors.toList());
  }
}
