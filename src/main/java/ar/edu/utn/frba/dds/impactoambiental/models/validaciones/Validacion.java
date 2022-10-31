package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.function.Predicate;

public interface Validacion<T> extends Predicate<T> {

  String getMensajeDeError();

  default Either<T> validar(T valor) {
    return test(valor) ? Either.exitoso(valor) : Either.fallido(getMensajeDeError());
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
