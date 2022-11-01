package ar.edu.utn.frba.dds.impactoambiental.dtos;

import java.time.LocalDate;
import java.util.UUID;

import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;

public class TrayectoResumenDto {
  String origen;
  String destino;
  LocalDate fecha;
  UUID codigoInvite;

  public TrayectoResumenDto(LocalDate fecha, UUID codigoInvite, String origen, String destino) {
    this.fecha = fecha;
    this.codigoInvite = codigoInvite;
    this.origen = origen;
    this.destino = destino;
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

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public UUID getCodigoInvite() {
    return codigoInvite;
  }

  public void setCodigoInvite(UUID codigoInvite) {
    this.codigoInvite = codigoInvite;
  }
}
