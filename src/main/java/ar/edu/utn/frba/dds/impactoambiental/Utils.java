package ar.edu.utn.frba.dds.impactoambiental;

import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface Utils {
  static Map<String, Object> mapOf(Object... args) {
    try {
      return Streams.stream(Iterables.partition(Arrays.asList(args), 2))
          .collect(HashMap::new, (map, pair) -> map.put((String) pair.get(0), pair.get(1)), HashMap::putAll);
    } catch (ClassCastException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Los argumentos deben ser pares de la forma (String, Object)");
    }
  }
}
