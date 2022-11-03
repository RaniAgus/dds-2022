package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static ar.edu.utn.frba.dds.impactoambiental.utils.MapUtil.mapOf;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.TypedQuery;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public interface Repositorio<T extends EntidadPersistente> extends EntityManagerOps, WithGlobalEntityManager {

  default boolean existe(Long id) {
    return obtenerPorID(id).isPresent();
  }

  default Optional<T> agregar(T entidad) {
    if (entidad.getId() != null) {
      return Optional.empty();
    }
    persist(entidad);
    return Optional.of(entidad).filter(e -> e.getId() != null);
  }

  default Optional<T> actualizar(T entidad) {
    return Optional.ofNullable(merge(entidad));
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

  default void eliminar(T entidad) {
    remove(entidad);
  }

  default void limpiar() {
    obtenerTodos().forEach(this::eliminar);
  }

  Class<T> clase();

}
