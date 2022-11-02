package ar.edu.utn.frba.dds.impactoambiental.controllers;

import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public interface Controller extends TransactionalOps, WithGlobalEntityManager {
  default String decode(String s) {
    try {
      return s == null ? null : URLDecoder.decode(s, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new UncheckedIOException(e);
    }
  }

  default String encode(String... strings) {
    try {
      return URLEncoder.encode(String.join("", strings), StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new UncheckedIOException(e);
    }
  }

  default String encode(Map<String, String> params) {
    return params.entrySet().stream()
        .map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
        .collect(Collectors.joining("&"));
  }

  default String render(String view, Map<String, Object> model) {
    return new HandlebarsTemplateEngine().render(new ModelAndView(model, view));
  }
}
