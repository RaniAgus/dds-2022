package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

import java.util.List;

public final class RepositorioDeSectoresTerritoriales extends Repositorio<SectorTerritorial> {
  private static final RepositorioDeSectoresTerritoriales instance = new RepositorioDeSectoresTerritoriales();

  public static RepositorioDeSectoresTerritoriales getInstance() {
    return instance;
  }

  private RepositorioDeSectoresTerritoriales() {
    super();
  }

  @Override
  public Class<SectorTerritorial> clase() {
    return SectorTerritorial.class;
  }

}
