package ar.edu.utn.frba.dds.impactoambiental.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MapUtil {
  static Map<String, Object> mapOf(Object... args) {
    try {
      return Streams.stream(Iterables.partition(Arrays.asList(args), 2))
          .collect(HashMap::new, (map, pair) -> map.put((String) pair.get(0), pair.get(1)), HashMap::putAll);
    } catch (ClassCastException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Los argumentos deben ser pares de la forma (String, Object)");
    }
  }

  static <K, V> Map<K, V> merge(BinaryOperator<V> mergeFunction, Map<K, V>... maps) {
    return Stream.of(maps)
        .flatMap(m -> m.entrySet().stream())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction));
  }
}
