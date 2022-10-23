package ar.edu.utn.frba.dds.impactoambiental.utils;

import java.util.Collections;
import java.util.List;

public class TryExitoso<T> implements Try<T> {
  private T valor;

  protected TryExitoso(T valor) {
    this.valor = valor;
  }

  public T getValor() {
    return valor;
  }

  public List<String> getErrores() {
    return Collections.emptyList();
  }
}
