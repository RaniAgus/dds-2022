package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TipoDeDocumento;

@Entity
public class UsuarioMiembro extends Usuario{

  String nombre;
  String apellido;
  String documento;
  @Enumerated(EnumType.STRING)
  TipoDeDocumento tipoDeDocumento;

  @OneToMany
  private List<Miembro> miembros;

  protected UsuarioMiembro() {}

  public UsuarioMiembro(String usuario, 
                        String contrasena, 
                        String nombre, 
                        String apellido,
                        String documento,
                        TipoDeDocumento tipoDeDocumento,
                        List<Miembro> miembros) {
    super(usuario, contrasena);
    this.miembros = miembros;
    this.nombre = nombre;
    this.apellido = apellido;
    this.documento = documento;
    this.tipoDeDocumento = tipoDeDocumento;
  }

  public List<Miembro> getMiembros() {
    return miembros;
  }

  public void agregarMiembro(Miembro miembro) {
    miembros.add(miembro);
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public String getDocumento() {
    return documento;
  }

  public TipoDeDocumento getTipoDeDocumento() {
    return tipoDeDocumento;
  }

  public String getHomeUrl() {
    return "/miembros/" + getId() + "/vinculaciones";
  }
}
