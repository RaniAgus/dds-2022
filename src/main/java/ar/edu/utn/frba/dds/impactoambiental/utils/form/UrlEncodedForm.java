package ar.edu.utn.frba.dds.impactoambiental.utils.form;

import spark.Request;

public class UrlEncodedForm implements Form {
  private final Request request;

  public UrlEncodedForm(Request request) {
    this.request = request;
  }

  @Override
  public String getString(String name) {
    return request.queryParams(name);
  }

  @Override
  public byte[] getFile(String param) {
    return null;
  }
}
