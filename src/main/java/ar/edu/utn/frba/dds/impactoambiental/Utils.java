package ar.edu.utn.frba.dds.impactoambiental;

import java.util.HashMap;
import java.util.Map;

public interface Utils {
  default Map<String, Object> mapOf(Object... args) {
    Map<String, Object> map = new HashMap<>();
    for (int i = 0; i < args.length; i += 2) {
      map.put((String) args[i], args[i + 1]);
    }
    return map;
  }
}
