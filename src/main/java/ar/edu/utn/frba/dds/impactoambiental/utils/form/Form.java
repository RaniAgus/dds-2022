package ar.edu.utn.frba.dds.impactoambiental.utils.form;

import spark.Request;

public interface Form {
  String getString(String name);
  byte[] getFile(String param);

  static Form of(Request req) {
    return req.contentType().startsWith("multipart/form-data")
        ? new MultipartForm(req)
        : new UrlEncodedForm(req);
  }
}
