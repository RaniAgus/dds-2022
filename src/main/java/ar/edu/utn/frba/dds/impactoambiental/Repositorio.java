package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.edu.utn.frba.dds.impactoambiental.Utils.mapOf;

public abstract class Repositorio<T extends EntidadPersistente> implements EntityManagerOps, WithGlobalEntityManager {
  protected List<T> repositorio;

  public Repositorio() {
    repositorio = cargarTodos();
  }

  public void agregar(T entidad) {
    persist(entidad);
    repositorio.add(entidad);
  }

  public Optional<T> obtenerPorID(Long id) {
    return repositorio.stream().filter(t->t.getId().equals(id)).findFirst();
  }

  private List<T> cargarTodos() {
    return createQuery("from " + clase().getSimpleName(), clase())
        .getResultList();
  }
  public List<T> obtenerTodos() {
    return repositorio;
  }
  protected Optional<T> buscar(Object... atributos) {
    return filtrar(atributos).stream().findFirst();
  }

  public List<T> filtrar(Object... atributos) {
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

   public void limpiar() {
    cargarTodos().forEach(this::remove);
    repositorio.clear();
  }

  protected abstract Class<T> clase();

}
