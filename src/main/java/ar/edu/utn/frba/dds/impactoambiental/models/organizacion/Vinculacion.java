package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Vinculacion extends EntidadPersistente {
  @OneToOne
  private final Miembro miembro;

  @ManyToOne
  private final Organizacion organizacion;

  @ManyToOne
  private final Sector sector;

  @Enumerated(EnumType.STRING)
  private EstadoVinculo estado;

  public Vinculacion(Miembro miembro, Organizacion organizacion, Sector sector) {
    this.miembro = miembro;
    this.organizacion = organizacion;
    this.sector = sector;
    this.estado = EstadoVinculo.PENDIENTE;
  }

  public void aceptar() {
    this.estado = this.estado.aceptar(this);
  }

  public void rechazar() {
    this.estado = this.estado.rechazar(this);
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

}

