package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;

import javax.persistence.*;
import java.util.List;

@Entity
public class Organizacion extends EntidadPersistente {
  private String razonSocial;
  @Embedded
  private Ubicacion ubicacionGeografica;
  private TipoDeOrganizacion tipoDeOrganizacion;
  private ClasificacionDeOrganizacion clasificacionDeOrganizacion;
  @OneToMany
  @JoinColumn(name = "organizacion_id")
  private List<Sector> sectores;
  @OneToMany
  @JoinColumn(name = "organizacion_id")
  private List<DatoActividad> datosActividad;
  @ManyToMany
  private List<Contacto> contactos;

  protected Organizacion() {
  }

  public Organizacion(String razonSocial,
                      Ubicacion ubicacionGeografica,
                      TipoDeOrganizacion tipoDeOrganizacion,
                      ClasificacionDeOrganizacion clasificacionDeOrganizacion,
                      List<Sector> sectores,
                      List<DatoActividad> datosActividad,
                      List<Contacto> contactos) {
    this.razonSocial = razonSocial;
    this.ubicacionGeografica = ubicacionGeografica;
    this.tipoDeOrganizacion = tipoDeOrganizacion;
    this.clasificacionDeOrganizacion = clasificacionDeOrganizacion;
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
}
