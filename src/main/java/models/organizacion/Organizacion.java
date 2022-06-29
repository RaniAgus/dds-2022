package models.organizacion;


import models.da.DatoActividad;
import models.geolocalizacion.Ubicacion;
import models.organizacion.notificaciones.ServicioDeNotificacion;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private final Ubicacion ubicacionGeografica;
  private final TipoDeOrganizacion tipoDeOrganizacion;
  private final ClasificacionDeOrganizacion clasificacionDeOrganizacion;
  private final List<Sector> sectores;
  private final List<DatoActividad> datosActividad;
  private List<ServicioDeNotificacion> serviciosDeNotificaciones;
  private List<Contacto> contactos;

  public void enviarGuia(String link) {
    serviciosDeNotificaciones
        .forEach(servicioDeNotificacion
            -> {
          try {
            servicioDeNotificacion.enviarGuiaRecomendacion(contactos, link);
          } catch (MessagingException e) {
            e.printStackTrace();
          }
        });
  }
  
  public Organizacion(String razonSocial, Ubicacion ubicacionGeografica,
                      TipoDeOrganizacion tipoDeOrganizacion,
                      ClasificacionDeOrganizacion clasificacionDeOrganizacion,
                      List<Sector> sectores,
                      List<DatoActividad> datosActividad) {
    this.razonSocial = razonSocial;
    this.clasificacionDeOrganizacion = clasificacionDeOrganizacion;
    this.tipoDeOrganizacion = tipoDeOrganizacion;
    this.ubicacionGeografica = ubicacionGeografica;
    this.sectores = new ArrayList<>(sectores);
    this.datosActividad = new ArrayList<>(datosActividad);
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
