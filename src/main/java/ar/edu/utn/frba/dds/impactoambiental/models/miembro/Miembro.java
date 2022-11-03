package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Miembro extends EntidadPersistente {
  @ManyToMany
  private List<Trayecto> trayectos;

  private String nombre;
  private String apellido;
  private String documento;
  private TipoDeDocumento tipoDeDocumento;

  public Miembro() {
  }

  public Miembro(String nombre, String apellido, String documento, TipoDeDocumento t, List<Trayecto> trayectos) {
    this.trayectos = trayectos;
    this.nombre = nombre;
    this.apellido = apellido;
    this.documento = documento;
    this.tipoDeDocumento = t;
  }

  public Miembro(UsuarioMiembro usuario){
    this.nombre = usuario.getNombre();
    this.apellido = usuario.getApellido();
    this.documento = usuario.getDocumento();
    this.tipoDeDocumento = usuario.getTipoDeDocumento();
    this.trayectos = new ArrayList<>();
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

  public List<Trayecto> getTrayectos() {
    return trayectos;
  }

  public List<Trayecto> getTrayectosEnPeriodo(Periodo periodo) {
    return getTrayectos().stream()
        .filter(trayecto -> trayecto.estaEnPeriodo(periodo))
        .collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Miembro miembro = (Miembro) o;
    return ((Miembro) o).getId().equals(this.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(trayectos, nombre, apellido, documento, tipoDeDocumento);
  }

  public Double huellaCarbonoPersonal(Periodo periodo) {
    return getTrayectosEnPeriodo(periodo).stream()
        .mapToDouble(Trayecto::carbonoEquivalente)
        .sum();
  }

  public Double impactoCarbonoEnOrganizacion(Organizacion organizacion, Periodo periodo) {
    return huellaCarbonoPersonal(periodo) / organizacion.huellaCarbono(periodo);
  }

  public void darDeAltaTrayecto(Trayecto trayecto) {
    trayectos.add(trayecto);
  }
}
