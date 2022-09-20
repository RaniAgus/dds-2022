package ar.edu.utn.frba.dds.impactoambiental.models.da;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.Optional;

public class RepositorioTipoDeConsumo implements WithGlobalEntityManager {

  public Optional<TipoDeConsumo> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("SELECT t FROM TipoDeConsumo t WHERE t.nombre LIKE :nombre", TipoDeConsumo.class)
        .setParameter("nombre", nombre)
        .getResultList().stream()
        .findFirst();
  }
}
