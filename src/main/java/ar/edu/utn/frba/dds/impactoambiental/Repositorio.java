package ar.edu.utn.frba.dds.impactoambiental;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.edu.utn.frba.dds.impactoambiental.Utils.mapOf;

public interface Repositorio<T> extends EntityManagerOps, WithGlobalEntityManager {

  default void agregar(T entidad) {
    persist(entidad);
  }

  default Optional<T> obtenerPorID(Long id) {
    return Optional.ofNullable(find(clase(), id));
  }

  default List<T> obtenerTodos() {
    return createQuery("from " + clase().getSimpleName(), clase())
        .getResultList();
  }

  default Optional<T> buscar(Object... atributos) {
    return filtrar(atributos).stream().findFirst();
  }

  default List<T> filtrar(Object... atributos) {
    Map<String, Object> atributosMap = mapOf(atributos);
    TypedQuery<T> query = createQuery(
        String.format("SELECT e FROM %s e WHERE %s",
            clase().getSimpleName(),
            atributosMap.keySet().stream()
                .map(clave -> String.format("e.%s LIKE :%s", clave, clave.replace(".", "")))
                .collect(Collectors.joining(" AND "))
        ), clase()
    );
    atributosMap.forEach((clave, valor) -> query.setParameter(clave.replace(".", ""), valor));
    return query.getResultList();
  }

  default void limpiar() {
    obtenerTodos().forEach(this::remove);
  }

  Class<T> clase();

}
