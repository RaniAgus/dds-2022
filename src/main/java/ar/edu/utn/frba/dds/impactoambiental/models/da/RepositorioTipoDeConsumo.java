package ar.edu.utn.frba.dds.impactoambiental.models.da;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

import java.util.Optional;

public class RepositorioTipoDeConsumo extends Repositorio<TipoDeConsumo> {

  public Optional<TipoDeConsumo> buscarPorNombre(String nombre) {
    return repositorio.stream().filter(x -> x.tieneNombre(nombre)).findFirst();
  }

  @Override
  public Class<TipoDeConsumo> clase() {
    return TipoDeConsumo.class;
  }
}
