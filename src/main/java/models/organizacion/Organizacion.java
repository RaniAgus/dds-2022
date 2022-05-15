package models.organizacion;

import models.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
  private final String razonSocial;
  private final Ubicacion ubicacionGeografica;
  private final TipoDeOrganizacion tipoDeOrganizacion;
  private final ClasificacionDeOrganizacion clasificacionDeOrganizacion;
  private final List<Sector> sectores;

  public Organizacion(String razonSocial, Ubicacion ubicacionGeografica,
                      TipoDeOrganizacion tipoDeOrganizacion,
                      ClasificacionDeOrganizacion clasificacionDeOrganizacion,
                      List<Sector> sectores) {
    this.razonSocial = razonSocial;
    this.clasificacionDeOrganizacion = clasificacionDeOrganizacion;
    this.tipoDeOrganizacion = tipoDeOrganizacion;
    this.ubicacionGeografica = ubicacionGeografica;
    this.sectores = new ArrayList<>(sectores);
  }

  public void darDeAltaSector(Sector sector) {
    this.sectores.add(sector);
  }

  public List<Sector> getSectores() {
    return sectores;
  }
}
