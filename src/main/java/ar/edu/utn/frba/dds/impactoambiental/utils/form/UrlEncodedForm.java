package ar.edu.utn.frba.dds.impactoambiental.utils.form;

import java.util.Optional;
import spark.Request;

public class UrlEncodedForm implements Form {
  private final Request request;

  public UrlEncodedForm(Request request) {
    this.request = request;
  }

  @Override
  public Optional<String> getString(String name) {
    return Optional.ofNullable(request.queryParams(name));
  }

  @Override
  public Optional<byte[]> getFile(String param) {
    return Optional.empty();
  }
}
