package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;

@Entity
public class UsuarioMiembro extends Usuario{
  @OneToMany
  private List<Miembro> miembros;

  protected UsuarioMiembro() {}

  public UsuarioMiembro(String usuario, String contrasena, List<Miembro> miembros) {
    super(usuario, contrasena);
    this.miembros = miembros;
  }

  public List<Miembro> getMiembros() {
    return miembros;
  }

  public void agregarMiembro(Miembro miembro) {
    miembros.add(miembro);
  }

  public String getHomeUrl() {
    return "/miembros/" + getId() + "/vinculaciones";
  }
}
