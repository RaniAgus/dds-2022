package ar.edu.utn.frba.dds.impactoambiental.dtos;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.EstadoVinculo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;

public class VinculacionDto {
  private Miembro miembro;
  private Organizacion organizacion;
  private Sector sector;
  private EstadoVinculo estado;

  public VinculacionDto(Miembro miembro, Organizacion organizacion, Sector sector, EstadoVinculo estado) {
      this.miembro = miembro;
      this.organizacion = organizacion;
      this.sector = sector;
      this.estado = estado;
  }

  public Miembro getMiembro() {
      return miembro;
  }

  public Organizacion getOrganizacion() {
      return organizacion;
  }

  public Sector getSector() {
      return sector;
  }

  public EstadoVinculo getEstado() {
      return estado;
  }

  public void setMiembro(Miembro miembro) {
      this.miembro = miembro;
  }

  public void setOrganizacion(Organizacion organizacion) {
      this.organizacion = organizacion;
  }

  public void setSector(Sector sector) {
      this.sector = sector;
  }

  public void setEstado(EstadoVinculo estado) {
      this.estado = estado;
  }
}
