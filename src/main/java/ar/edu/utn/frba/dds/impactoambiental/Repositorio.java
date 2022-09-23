package ar.edu.utn.frba.dds.impactoambiental;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T> extends WithGlobalEntityManager {

  default void agregar(T entidad) {
    entityManager().persist(entidad);
  }

  default T obtenerPorID(Long id) {
    return entityManager().find(this.clase(), id);
  }

  default Optional<T> obtenerPorAtributo(String atributo, Object valor) {
    return entityManager()
        .createQuery(
            String.format("SELECT c FROM %s c WHERE c.%s LIKE :%s",
                this.clase().getSimpleName(),
                atributo,
                atributo
            ),
            this.clase()
        )
        .setParameter(atributo, valor)
        .getResultList().stream()
        .findFirst();
  }

  default List<T> obtenerTodos() {
    return entityManager().createQuery("from " + this.clase().getSimpleName(), this.clase())
        .getResultList();
  }

  default void limpiar() {
    obtenerTodos().forEach(it -> entityManager().remove(it));
  }

  Class<T> clase();

}
