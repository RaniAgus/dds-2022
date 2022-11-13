package ar.edu.utn.frba.dds.impactoambiental.dtos;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.EstadoVinculo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import java.util.stream.Collectors;

public class VinculacionDto {
  private Long id;
  private Miembro miembro;
  private Organizacion organizacion;
  private Sector sector;
  private EstadoVinculo estado;
  private boolean sePuedenVerLosTrayectos;
  private boolean seleccionado;

  public VinculacionDto(Long id, Miembro miembro, Organizacion organizacion, Sector sector, EstadoVinculo estado) {
      this.id = id;
      this.miembro = miembro;
      this.organizacion = organizacion;
      this.sector = sector;
      this.estado = estado;
      this.sePuedenVerLosTrayectos = estado.equals(EstadoVinculo.ACEPTADO);
      this.seleccionado = false;
  }

  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
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

  public boolean getSePuedenVerLosTrayectos() {
    return sePuedenVerLosTrayectos;
  }

  public static VinculacionDto from(Organizacion organizacion, Miembro miembro) {
    Sector sector = organizacion.getSectores().stream()
        .filter(it -> it.getAllMiembros().stream().anyMatch(m -> m.getId().equals(miembro.getId())))
        .collect(Collectors.toList()).get(0);

    Vinculacion vinculacion = sector.getVinculaciones().stream()
        .filter(it -> it.getMiembro().getId().equals(miembro.getId()))
        .collect(Collectors.toList()).get(0);

    return new VinculacionDto(
        vinculacion.getId(),
        miembro,
        organizacion,
        sector,
        vinculacion.getEstado()
    );
  }

  public boolean getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(boolean seleccionado) {
    this.seleccionado = seleccionado;
  }
}
