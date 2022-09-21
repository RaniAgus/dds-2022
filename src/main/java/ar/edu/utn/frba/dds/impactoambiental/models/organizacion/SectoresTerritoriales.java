package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

public final class SectoresTerritoriales implements Repositorio<SectorTerritorial> {
  private static final SectoresTerritoriales instance = new SectoresTerritoriales();

  public static SectoresTerritoriales getInstance() {
    return instance;
  }

  private SectoresTerritoriales() {
  }

  @Override
  public Class<SectorTerritorial> clase() {
    return SectorTerritorial.class;
  }

}
