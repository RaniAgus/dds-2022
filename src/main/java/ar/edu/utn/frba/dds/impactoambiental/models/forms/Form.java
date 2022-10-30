package ar.edu.utn.frba.dds.impactoambiental.models.forms;

import java.util.Optional;

import io.vavr.control.Either;
import spark.Request;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public abstract class Form {
  public abstract Optional<String> getParam(String param);

  public Either<String,String> getParamOrError(String param, String error) {
    return getParam(param).isPresent()? right(getParam(param).get()): left(error);
  }

  public Either<String,String> getParamOrDefault(String param, String defaultValue) {
    Either<String,String> result;
    return getParam(param).isPresent()? right(getParam(param).get()): left(defaultValue);
  }

  public abstract Optional<byte[]> getFile(String param);

  static Form of(Request req) {
    return req.contentType().startsWith("multipart/form-data")
        ? new MultipartForm(req)
        : new UrlEncodedForm(req);
  }
}
