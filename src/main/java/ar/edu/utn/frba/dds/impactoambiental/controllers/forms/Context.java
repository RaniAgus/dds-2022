package ar.edu.utn.frba.dds.impactoambiental.controllers.forms;

import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import spark.Request;

public class Context {
  private Request req;

  private Context(Request req) {
    this.req = req;
  }

  public Either<String> getPathParam(String param, String error) {
    return Optional.ofNullable(req.params(param))
        .map(valor -> valor.isEmpty() ? null : valor)
        .map(Either::exitoso)
        .orElseGet(() -> Either.fallido(error));
  }

  public <T> Either<T> getSessionAttribute(String name, String error) {
    return Either.desde(() -> req.session().attribute(name), error);
  }

  public <T> void setSessionAttribute(String key, T value) {
    req.session().attribute(key, value);
  }

  public <T> Either<T> getRequestAttribute(String name, String error) {
    return Either.exitoso(req.<T>attribute(name)).filter(Objects::nonNull, error);
  }

  public <T> void setRequestAttribute(String key, T value) {
    req.attribute(key, value);
  }

  public <T> T computeSessionAttributeIfAbsent(String name, Supplier<T> defaultValue) {
    if (req.session().attribute(name) == null) {
      req.session().attribute(name, defaultValue.get());
    }
    return req.session().attribute(name);
  }

  public static Context of(Request req) {
    return new Context(req);
  }

  public boolean hasBodyParams() {
    return !req.queryParams().isEmpty();
  }
}
