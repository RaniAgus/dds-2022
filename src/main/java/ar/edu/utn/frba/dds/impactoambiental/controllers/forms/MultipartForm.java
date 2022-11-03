package ar.edu.utn.frba.dds.impactoambiental.controllers.forms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.eclipse.jetty.io.RuntimeIOException;
import spark.Request;
import spark.utils.IOUtils;

public class MultipartForm extends Form {
  private final Request request;

  public MultipartForm(Request request) {
    this.request = request;
  }

  @Override
  public Optional<String> getParam(String param) {
    return getPart(param)
        .map(part -> isFile(part) ? null : part)
        .map(MultipartForm::readAllBytes)
        .map(String::new);
  }

  @Override
  public Optional<byte[]> getFile(String param) {
    return getPart(param)
        .map(part -> isFile(part) ? part : null)
        .map(MultipartForm::readAllBytes);
  }

  private Optional<Part> getPart(String name) {
    request.attribute("org.eclipse.jetty.multipartConfig",
        new MultipartConfigElement("/temp"));
    try {
      return Optional.ofNullable(request.raw().getPart(name));
    } catch (IOException | ServletException e) {
      throw new RuntimeIOException(e);
    }
  }

  private static Boolean isFile(Part part) {
    return part.getSubmittedFileName() != null;
  }

  private static byte[] readAllBytes(Part part) {
    try (InputStream is = part.getInputStream()) {
      return IOUtils.toByteArray(is);
    } catch (IOException e) {
      throw new RuntimeIOException(e);
    }
  }
}
