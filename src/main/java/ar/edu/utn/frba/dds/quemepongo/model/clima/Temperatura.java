package ar.edu.utn.frba.dds.quemepongo.model.clima;

public enum Temperatura {
  FRIO,
  TEMPLADO,
  CALIDO;

  public static Temperatura ofFahrenheit(double fahrenheit) {
    return ofCelsius((fahrenheit - 32) / 1.8);
  }

  public static Temperatura ofCelsius(double celsius) {
    return celsius >= 20 ? CALIDO : celsius >= 10 ? TEMPLADO : FRIO;
  }
}
