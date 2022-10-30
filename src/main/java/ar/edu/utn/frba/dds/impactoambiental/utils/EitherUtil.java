package ar.edu.utn.frba.dds.impactoambiental.utils;

import io.vavr.control.Either;
import java.util.List;
import java.util.stream.Collectors;

public interface EitherUtil {
  static <L, R> boolean allRight(List<Either<L, R>> eithers) {
    return eithers.stream().allMatch(Either::isRight);
  }

  static <L, R> List<L> collectLefts(List<Either<L, R>> eithers) {
    return eithers.stream()
        .filter(Either::isLeft)
        .map(Either::getLeft)
        .collect(Collectors.toList());
  }
}
