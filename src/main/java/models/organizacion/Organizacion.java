package models.organizacion;

import models.da.DatoActividad;
import models.geolocalizacion.Ubicacion;
import models.notificaciones.Notificador;

import java.util.List;

public class Organizacion {
  private String razonSocial;
  private Ubicacion ubicacionGeografica;
  private TipoDeOrganizacion tipoDeOrganizacion;
  private ClasificacionDeOrganizacion clasificacionDeOrganizacion;
  private List<Sector> sectores;
  private List<DatoActividad> datosActividad;
  private List<Notificador> notificadores;
  private List<Contacto> contactos;

  public Organizacion(String razonSocial,
                      Ubicacion ubicacionGeografica,
                      TipoDeOrganizacion tipoDeOrganizacion,
                      ClasificacionDeOrganizacion clasificacionDeOrganizacion,
                      List<Sector> sectores,
                      List<DatoActividad> datosActividad,
                      List<Notificador> notificadores,
                      List<Contacto> contactos) {
    this.razonSocial = razonSocial;
    this.ubicacionGeografica = ubicacionGeografica;
    this.tipoDeOrganizacion = tipoDeOrganizacion;
    this.clasificacionDeOrganizacion = clasificacionDeOrganizacion;
    this.sectores = sectores;
    this.datosActividad = datosActividad;
    this.notificadores = notificadores;
    this.contactos = contactos;
  }

  public void enviarGuia(String link) {
    notificadores.forEach(it -> it.enviarGuiaRecomendacion(contactos, link));
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
}
