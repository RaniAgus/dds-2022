package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.SectorTerritorial;

public final class RepositorioDeSectoresTerritoriales implements Repositorio<SectorTerritorial> {
  private static final RepositorioDeSectoresTerritoriales instance = new RepositorioDeSectoresTerritoriales();

  public static RepositorioDeSectoresTerritoriales getInstance() {
    return instance;
  }

  private RepositorioDeSectoresTerritoriales() {}

  @Override
  public Class<SectorTerritorial> clase() {
    return SectorTerritorial.class;
  }

}
