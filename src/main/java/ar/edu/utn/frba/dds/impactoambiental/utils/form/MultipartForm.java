package ar.edu.utn.frba.dds.impactoambiental.utils.form;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.eclipse.jetty.io.RuntimeIOException;
import spark.Request;
import spark.utils.IOUtils;

public class MultipartForm implements Form {
  private final Request request;

  public MultipartForm(Request request) {
    this.request = request;
  }

  @Override
  public String getString(String param) {
    return getPart(param)
        .map(part -> isFile(part) ? null : part)
        .map(MultipartForm::readAllBytes)
        .map(String::new)
        .orElse(null);
  }

  @Override
  public byte[] getFile(String param) {
    return getPart(param)
        .map(part -> isFile(part) ? part : null)
        .map(MultipartForm::readAllBytes)
        .orElse(null);
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
