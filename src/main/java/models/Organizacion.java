package models;

import static java.util.Objects.requireNonNull;

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
    this.razonSocial = requireNonNull(razonSocial, "La razon social no puede ser nula");
    this.clasificacionDeOrganizacion = requireNonNull(clasificacionDeOrganizacion,
        "La clasificacion no pude ser nula");
    this.tipoDeOrganizacion = requireNonNull(tipoDeOrganizacion,
        "La organizacion debe tener un tipo");
    this.ubicacionGeografica = requireNonNull(ubicacionGeografica,
        "La ubicacion no puede ser nula");
    this.sectores = new ArrayList<>(sectores);
  }

  public void darDeAltaSector(Sector sector) {
    this.sectores.add(sector);
  }

  public List<Sector> getSectores() {
    return new ArrayList<>(sectores);
  }
}
