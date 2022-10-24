package ar.edu.utn.frba.dds.impactoambiental.utils.form;

import java.util.Optional;
import spark.Request;

public class UrlEncodedForm extends Form {
  private final Request request;

  public UrlEncodedForm(Request request) {
    this.request = request;
  }

  @Override
  public Optional<String> getParam(String param) {
    return Optional.ofNullable(request.queryParams(param));
  }

  @Override
  public Optional<byte[]> getFile(String param) {
    return Optional.empty();
  }
}
