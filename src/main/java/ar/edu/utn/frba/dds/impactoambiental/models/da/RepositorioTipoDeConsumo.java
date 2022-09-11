package ar.edu.utn.frba.dds.impactoambiental.models.da;

import java.util.List;
import java.util.Optional;

public class RepositorioTipoDeConsumo {
  private List<TipoDeConsumo> tiposDeConsumo;

  public RepositorioTipoDeConsumo(List<TipoDeConsumo> tiposDeConsumo) {
    this.tiposDeConsumo = tiposDeConsumo;
  }

  public Optional<TipoDeConsumo> buscarPorNombre(String nombre) {
    return tiposDeConsumo.stream()
        .filter(it -> it.tieneNombre(nombre))
        .findFirst();
  }
}
