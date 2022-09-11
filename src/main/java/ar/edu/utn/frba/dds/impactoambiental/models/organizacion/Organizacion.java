package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.da.DatoActividad;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Ubicacion;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;

import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private final Ubicacion ubicacionGeografica;
  private final TipoDeOrganizacion tipoDeOrganizacion;
  private final ClasificacionDeOrganizacion clasificacionDeOrganizacion;
  private final List<Sector> sectores;
  private final List<DatoActividad> datosActividad;
  private List<Contacto> contactos;

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

  public Double huellaCarbonoDA(Periodo periodo) {
    return this.datosActividad.stream()
          .filter(da -> da.estaEnPeriodo(periodo))
          .mapToDouble(DatoActividad::carbonoEquivalente).sum();
  }

  public Double huellaCarbono(Periodo periodo) {
    return huellaCarbonoTrayectos(periodo) + huellaCarbonoDA(periodo);
  }
}
