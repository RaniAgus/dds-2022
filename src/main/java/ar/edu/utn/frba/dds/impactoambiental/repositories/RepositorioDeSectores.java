package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import java.util.UUID;

public class RepositorioDeSectores implements Repositorio<Sector> {
  private static final RepositorioDeSectores instance = new RepositorioDeSectores();

  public static RepositorioDeSectores getInstance() {
    return instance;
  }

  private RepositorioDeSectores() {}

  public Either<Sector> buscarPorCodigoInvite(UUID codigoInvite) {
    return buscar("codigoInvite", codigoInvite)
        .map(Either::exitoso)
        .orElseGet(() -> Either.fallido("El código ingresado no existe"));
  }

  @Override
  public Class<Sector> clase() {
    return Sector.class;
  }
}
