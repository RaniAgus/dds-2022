package ar.edu.utn.frba.dds.impactoambiental;

import com.google.common.collect.ImmutableMap;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Repositorio<T> extends EntityManagerOps, WithGlobalEntityManager {

  default void agregar(T entidad) {
    persist(entidad);
  }

  default T obtenerPorID(Long id) {
    return find(clase(), id);
  }

  default Optional<T> obtenerPorAtributo(String clave, Object valor) {
    return obtenerPorAtributos(ImmutableMap.of(clave, valor));
  }

  default Optional<T> obtenerPorAtributos(Map<String, Object> atributos) {
    return filtrarPorAtributos(atributos).stream().findFirst();
  }

  default List<T> obtenerTodos() {
    return createQuery("from " + clase().getSimpleName(), clase())
        .getResultList();
  }

  default List<T> filtrarPorAtributo(String clave, Object valor) {
    return filtrarPorAtributos(ImmutableMap.of(clave, valor));
  }

  default List<T> filtrarPorAtributos(Map<String, Object> atributos) {
    TypedQuery<T> query = createQuery(
        String.format("SELECT e FROM %s e WHERE %s",
            clase().getSimpleName(),
            atributos.keySet().stream()
                .map(clave -> String.format("e.%s LIKE :%s", clave, clave.replace(".", "")))
                .collect(Collectors.joining(" AND "))
        ), clase()
    );
    atributos.forEach((clave, valor) -> query.setParameter(clave.replace(".", ""), valor));
    return query.getResultList();
  }

  default void limpiar() {
    obtenerTodos().forEach(this::remove);
  }

  Class<T> clase();

}
