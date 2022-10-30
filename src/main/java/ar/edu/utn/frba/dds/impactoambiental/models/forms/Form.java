package ar.edu.utn.frba.dds.impactoambiental.models.forms;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import io.vavr.control.Either;
import java.util.Optional;
import spark.Request;

public abstract class Form {
  public abstract Optional<String> getParam(String param);

  public Either<String, String> getParamOrError(String param, String error) {
    Optional<Either<String, String>> o = getParam(param).map(Either::right);
    return o.orElseGet(() -> left(error));
  }

  public Either<String, String> getParamOrDefault(String param, String defaultValue) {
    Optional<Either<String, String>> o = getParam(param).map(Either::right);
    return o.orElseGet(() -> right(defaultValue));
  }

  public abstract Optional<byte[]> getFile(String param);

  static Form of(Request req) {
    return req.contentType().startsWith("multipart/form-data")
        ? new MultipartForm(req)
        : new UrlEncodedForm(req);
  }
}
