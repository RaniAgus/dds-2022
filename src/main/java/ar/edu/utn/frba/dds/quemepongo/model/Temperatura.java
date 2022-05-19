package ar.edu.utn.frba.dds.quemepongo.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public enum Temperatura {
  FRIO,
  TEMPLADO,
  CALIDO;

  public static Temperatura of(String unit, double value) {
    Map<String, Function<Double, Temperatura>> converters = ImmutableMap.of(
        "F", Temperatura::ofFahrenheit,
        "C", Temperatura::ofCelsius
    );
    return Objects.requireNonNull(converters.get(unit)).apply(value);
  }

  public static Temperatura ofFahrenheit(double fahrenheit) {
    return ofCelsius((fahrenheit - 32) / 1.8);
  }

  public static Temperatura ofCelsius(double celsius) {
    return celsius >= 20 ? CALIDO : celsius >= 10 ? TEMPLADO : FRIO;
  }
}
