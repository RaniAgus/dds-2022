package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

@Entity
public class UsuarioOrganizacion extends Usuario {
  @OneToOne
  private Organizacion organizacion;

  protected UsuarioOrganizacion() {}

  public UsuarioOrganizacion(String usuario, String contrasena, Organizacion organizacion) {
    super(usuario, contrasena);
    this.organizacion = organizacion;
  }

  public Organizacion getOrganizacion() {
    return organizacion;
  }

  public String getHomeUrl() {
    return "/organizaciones/" + getId() + "/vinculaciones";
  }
}
