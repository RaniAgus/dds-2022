package ar.edu.utn.frba.dds.impactoambiental;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public interface Repositorio<T> extends WithGlobalEntityManager {
  
  default void agregar(T entidad) {
    entityManager().persist(entidad);
  }

  default T obtenerPorID(Long id) {
    return entityManager().find(this.clase(), id);
  }

  default List<T> obtenerTodos() {
    return entityManager().createQuery("from " + this.clase().getSimpleName(), this.clase())
      .getResultList();
  }

  default void limpiar() {
    obtenerTodos().forEach(it -> entityManager().remove(it));
  }

  public abstract Class<T> clase();
  
}
