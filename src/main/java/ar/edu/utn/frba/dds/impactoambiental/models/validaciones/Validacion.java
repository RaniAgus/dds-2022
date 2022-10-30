package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import io.vavr.control.Either;

import java.util.function.Predicate;

import static io.vavr.API.Right;
import static io.vavr.control.Either.left;

public interface Validacion<T> {

  Predicate<T> validationCondition();
  public String getMensajeDeError();

  default Either<String, T> validar(T valor) {
    if (validationCondition().test(valor)) {
      return Right(valor);
    }
    return left(getMensajeDeError());
  }


  static <T> Validacion<T> create(Predicate<T> chequeo, String mensajeDeError) {
    return new Validacion<T>() {
      @Override
      public Either<String, T> validar(T valor) {
        if (validationCondition().test(valor)) {
          return Right(valor);
        }
        return left(mensajeDeError);
      }

      @Override
      public Predicate<T> validationCondition() {
        return chequeo;
      }

      @Override
      public String getMensajeDeError() {
        return mensajeDeError;
      }
    };
  }
}



