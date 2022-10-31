package ar.edu.utn.frba.dds.impactoambiental.models.forms;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;
import java.util.Optional;
import spark.Request;

public abstract class Form {
  public abstract Optional<String> getParam(String param);

  public Either<String> getParamOrError(String param, String error) {
    return getParam(param).map(Either::exitoso)
        .orElseGet(() -> Either.fallido(error));
  }

  public abstract Optional<byte[]> getFile(String param);

  public static Form of(Request req) {
    return req.contentType().startsWith("multipart/form-data")
        ? new MultipartForm(req)
        : new UrlEncodedForm(req);
  }
}
