package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;

@Entity
public class UsuarioSectorTerritorial extends Usuario {
  @OneToOne
  private SectorTerritorial sectorTerritorial;
  
  protected UsuarioSectorTerritorial() {}

  public UsuarioSectorTerritorial(String usuario, String contrasena, SectorTerritorial sectorTerritorial) {
    super(usuario, contrasena);
    this.sectorTerritorial = sectorTerritorial;
  }

  public SectorTerritorial getSectorTerritorial() {
    return sectorTerritorial;
  }

  public String getHomeUrl() {
    return "/sectoresterritoriales/" + getId() + "/reportes";
  }
}
