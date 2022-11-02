package ar.edu.utn.frba.dds.impactoambiental.controllers.forms;

import ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones.Either;
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

  public <T> T computeSessionAttributeIfAbsent(String name, Supplier<T> defaultValue) {
    if (req.session().attribute(name) == null) {
      req.session().attribute(name, defaultValue.get());
    }
    return req.session().attribute(name);
  }

  public static Context of(Request req) {
    return new Context(req);
  }
}
