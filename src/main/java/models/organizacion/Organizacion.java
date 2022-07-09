package models.organizacion;


import models.da.DatoActividad;
import models.da.Periodicidad;
import models.geolocalizacion.Ubicacion;
import models.miembro.Trayecto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private final Ubicacion ubicacionGeografica;
  private final TipoDeOrganizacion tipoDeOrganizacion;
  private final ClasificacionDeOrganizacion clasificacionDeOrganizacion;
  private final List<Sector> sectores;
  private final List<DatoActividad> datosActividad;
  
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

  public Double huellaCarbonoTrayectos(Periodicidad periodicidad){
    return this.sectores.stream()
          .flatMap(s -> s.getVinculacionesSegunEstado(EstadoVinculo.ACEPTADO).stream())
          .map(Vinculacion::getMiembro)
          .flatMap(it -> it.getTrayectos().stream())
          .distinct()
          .mapToDouble(Trayecto::carbonoEquivalente)
          .sum() * periodicidad.diasLaborales();
  }

  public Double huellaCarbonoDA(LocalDate fecha, Periodicidad periodicidad){
    return this.datosActividad.stream()
          .filter(da -> da.estaEnPeriodo(fecha, periodicidad))
          .mapToDouble(DatoActividad::carbonoEquivalente).sum();
  }

  public Double huellaCarbono(LocalDate fecha, Periodicidad periodicidad) {
    return huellaCarbonoTrayectos(periodicidad) 
        + huellaCarbonoDA(fecha, periodicidad);
  }
}
