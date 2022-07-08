package models.organizacion;

import models.da.DatoActividad;
import models.geolocalizacion.Ubicacion;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private final Ubicacion ubicacionGeografica;
  private final TipoDeOrganizacion tipoDeOrganizacion;
  private final ClasificacionDeOrganizacion clasificacionDeOrganizacion;
  private final List<Sector> sectores;
  private final List<DatoActividad> datosActividad;
  private List<Contacto> contactos;

  public Organizacion(String razonSocial, Ubicacion ubicacionGeografica, TipoDeOrganizacion tipoDeOrganizacion, ClasificacionDeOrganizacion clasificacionDeOrganizacion, List<Sector> sectores, List<DatoActividad> datosActividad, List<Contacto> contactos) {
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

  public void agregarContacto(Contacto contacto) { this.contactos.add(contacto); }
}
