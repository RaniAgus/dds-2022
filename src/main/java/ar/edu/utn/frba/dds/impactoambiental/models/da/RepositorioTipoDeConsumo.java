package ar.edu.utn.frba.dds.impactoambiental.models.da;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

import java.util.Optional;

public class RepositorioTipoDeConsumo implements Repositorio<TipoDeConsumo> {

  public Optional<TipoDeConsumo> buscarPorNombre(String nombre) {
    return obtenerPorAtributo("nombre", nombre);
  }

  @Override
  public Class<TipoDeConsumo> clase() {
    return TipoDeConsumo.class;
  }
}
