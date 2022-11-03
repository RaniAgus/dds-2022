package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Contacto;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Organizacion extends EntidadPersistente {
  private String razonSocial;
  @Embedded
  private Ubicacion ubicacionGeografica;
  @Enumerated(EnumType.STRING)
  private TipoDeOrganizacion tipo;
  @Enumerated(EnumType.STRING)
  private ClasificacionDeOrganizacion clasificacion;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "organizacion_id")
  private List<Sector> sectores;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "organizacion_id")
  private List<DatoActividad> datosActividad;
  @OneToMany
  @JoinColumn(name = "organizacion_id")
  private List<Contacto> contactos;

  protected Organizacion() {
  }

  public Organizacion(String razonSocial,
                      Ubicacion ubicacionGeografica,
                      TipoDeOrganizacion tipo,
                      ClasificacionDeOrganizacion clasificacion,
                      List<Sector> sectores,
                      List<DatoActividad> datosActividad,
                      List<Contacto> contactos) {
    this.razonSocial = razonSocial;
    this.ubicacionGeografica = ubicacionGeografica;
    this.tipo = tipo;
    this.clasificacion = clasificacion;
    this.sectores = sectores;
    this.datosActividad = datosActividad;
    this.contactos = contactos;
  }

  public void enviarGuia(String link) {
    contactos.forEach(contacto -> contacto.enviarGuia(link));
  }

  public void darDeAltaSector(Sector sector) {
    this.sectores.add(sector);
  }

  public List<Sector> getSectores() {
    return sectores;
  }

  public List<Miembro> getMiembros() {
    return sectores.stream().flatMap(s -> s.getMiembros().stream()).collect(Collectors.toList());
  }

  public void agregarDatosActividad(List<DatoActividad> nuevosDatos) {
    this.datosActividad.addAll(nuevosDatos);
  }

  public List<DatoActividad> getDatosActividad() {
    return datosActividad;
  }

  public void agregarContacto(Contacto contacto) {
    this.contactos.add(contacto);
  }

  public Double huellaCarbonoTrayectos(Periodo periodo) {
    return this.sectores.stream()
        .flatMap(sector -> sector.getTrayectosEnPeriodo(periodo).stream())
        .distinct()
        .mapToDouble(Trayecto::carbonoEquivalente)
        .sum();
  }

  public Double huellaCarbonoTrayectosSegunConsumo(Periodo periodo, TipoDeConsumo tipoDeConsumo) {
    return this.sectores.stream()
        .flatMap(sector -> sector.getTrayectosEnPeriodo(periodo).stream())
        .distinct()
        .mapToDouble(trayecto -> trayecto.carbonoEquivalenteSegunConsumo(tipoDeConsumo))
        .sum();
  }

  public Double huellaCarbonoDA(Periodo periodo) {
    return this.datosActividad.stream()
        .filter(da -> da.estaEnPeriodo(periodo))
        .mapToDouble(DatoActividad::carbonoEquivalente).sum();
  }

  public Double huellaCarbonoDASegunConsumo(Periodo periodo, TipoDeConsumo tipoDeConsumo) {
    return this.datosActividad.stream()
        .filter(da -> da.estaEnPeriodo(periodo))
        .filter(da -> da.tieneTipoDeConsumo(tipoDeConsumo))
        .mapToDouble(DatoActividad::carbonoEquivalente).sum();
  }

  public Double huellaCarbono(Periodo periodo) {
    return huellaCarbonoTrayectos(periodo) + huellaCarbonoDA(periodo);
  }

  public Double huellaCarbonoSegunConsumo(Periodo periodo, TipoDeConsumo tipoDeConsumo) {
    return huellaCarbonoTrayectosSegunConsumo(periodo, tipoDeConsumo) + huellaCarbonoDASegunConsumo(periodo, tipoDeConsumo);
  }

  public TipoDeOrganizacion getTipo() {
    return tipo;
  }

  public String getRazonSocial() {
    return razonSocial;
  }
}
