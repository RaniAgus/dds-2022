package ar.edu.utn.frba.dds.impactoambiental.models.forms;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Try;
import java.util.Optional;
import spark.Request;

public abstract class Form {
  public abstract Optional<String> getParam(String param);

  public Try<String> getParamOrError(String param, String error) {
    return getParam(param).map(Try::exitoso)
        .orElseGet(() -> Try.fallido(error));
  }

  public Try<String> getParamOrDefault(String param, String defaultValue) {
    return getParam(param).map(Try::exitoso)
        .orElseGet(() -> Try.exitoso(defaultValue));
  }

  public abstract Optional<byte[]> getFile(String param);

  static Form of(Request req) {
    return req.contentType().startsWith("multipart/form-data")
        ? new MultipartForm(req)
        : new UrlEncodedForm(req);
  }
}
