package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import java.util.Optional;

public class RepositorioTipoDeConsumo implements Repositorio<TipoDeConsumo> {
  private static final RepositorioTipoDeConsumo instance = new RepositorioTipoDeConsumo();

  private RepositorioTipoDeConsumo() {}

  public static RepositorioTipoDeConsumo getInstance() {
    return instance;
  }

  public Optional<TipoDeConsumo> buscarPorNombre(String nombre) {
    return buscar("nombre", nombre);
  }

  @Override
  public Class<TipoDeConsumo> clase() {
    return TipoDeConsumo.class;
  }
}
