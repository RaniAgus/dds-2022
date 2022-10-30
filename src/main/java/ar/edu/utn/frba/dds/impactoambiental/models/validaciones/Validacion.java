package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import io.vavr.control.Either;
import java.util.function.Predicate;

public interface Validacion<T> extends Predicate<T> {

  String getMensajeDeError();

  default Either<String, T> validar(T valor) {
    return test(valor) ? right(valor) : left(getMensajeDeError());
  }

  static <T> Validacion<T> create(Predicate<T> chequeo, String mensajeDeError) {
    return new Validacion<T>() {
      @Override
      public boolean test(T valor) {
        return chequeo.test(valor);
      }

      @Override
      public String getMensajeDeError() {
        return mensajeDeError;
      }
    };
  }
}
