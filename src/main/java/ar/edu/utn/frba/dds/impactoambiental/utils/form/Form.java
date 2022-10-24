package ar.edu.utn.frba.dds.impactoambiental.utils.form;

import java.util.Optional;
import spark.Request;

public interface Form {
  Optional<String> getString(String name);
  Optional<byte[]> getFile(String param);

  static Form of(Request req) {
    return req.contentType().startsWith("multipart/form-data")
        ? new MultipartForm(req)
        : new UrlEncodedForm(req);
  }
}
