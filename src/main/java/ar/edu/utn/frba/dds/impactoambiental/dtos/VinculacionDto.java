package ar.edu.utn.frba.dds.impactoambiental.dtos;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;

public class VinculacionDto {
  private Miembro miembro;
  private Organizacion organizacion;
  private Sector sector;

  public VinculacionDto(Miembro miembro, Organizacion organizacion, Sector sector) {
      this.miembro = miembro;
      this.organizacion = organizacion;
      this.sector = sector;
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

  public void setMiembro(Miembro miembro) {
      this.miembro = miembro;
  }

  public void setOrganizacion(Organizacion organizacion) {
      this.organizacion = organizacion;
  }

  public void setSector(Sector sector) {
      this.sector = sector;
  }
}
