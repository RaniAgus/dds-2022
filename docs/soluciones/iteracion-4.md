# Cuarta Iteración

## Solución planteada

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-4.puml)

### Requerimiento 1

> Como usuario/a de QuéMePongo, quiero poder recibir sugerencias de atuendos que
tengan una prenda para cada categoría, aunque a futuro podrán tener más (Ej.:
Una remera, un pantalón, zapatos y un gorro).

Continúo utilizando la clase `Atuendo` para modelar una sugerencia, y el
`Guardarropas` pasa a exponer un mensaje `sugerir()` que devuelve un `Atuendo`
aleatorio conformado por cuatro `Prendas` sugeribles, una de cada `Categoria`,
mientras que `getPrendasSugeribles(categoria)` pasa a ser privado:

```kotlin
class Guardarropas {
    fun sugerir(): Atuendo => new Atuendo(
          getPrendasSugeribles(SUPERIOR).atRandom(),
          getPrendasSugeribles(INFERIOR).atRandom(),
          getPrendasSugeribles(CALZADO).atRandom(),
          getPrendasSugeribles(ACCESORIO).atRandom()
    )
}
```
> Nota: como en Java el `atRandom()` no existe, tuve que implementarlo distinto.

Considerando el principio YAGNI, elijo ignorar el hecho de que a futuro una
sugerencia pueda incluir más de un atuendo ya que no es un requerimiento actual.

### Requerimiento 2

> Como administrador/a de QuéMePongo, quiero poder configurar fácilmente
diferentes servicios de obtención del clima para ajustarme a las cambiantes
condiciones económicas.

Se crea una interfaz `ServicioMeteorologico` con un mensaje para obtener la
`Temperatura` en base a él:

```ts
interface ServicioMeteorologico {
  getTemperatura(): Temperatura
}
```

### Requerimiento 3

> Como usuario/a de QuéMePongo, quiero poder conocer las condiciones climáticas de
  Buenos Aires en un momento dado para obtener sugerencias acordes.

Se crea una clase `AccuWeather` que convierta la respuesta del SDK provisto a
una instancia de `Temperatura`:

```ts
class AccuWeather {
  temperatura: Temperatura

  actualizarClima(): void {
    const data = new AccuWeatherAPI().getWeather('Buenos Aires, Argentina')[0];

    this.temperatura = Temperatura.of(data.Temperature.Unit, data.Temperature.Value);
  }
}
```

### Requerimiento 4

> Como usuario/a de QuéMePongo, quiero que al generar una sugerencia las prendas
  sean acordes a la temperatura actual sabiendo que para cada prenda habrá una
  temperatura hasta la cual es adecuada. (Ej.: "Remera de mangas largas" no es
  apta a más de 20°C)

Modelé a la `Temperatura` como un `enum` que dependiendo de la temperatura en
Celsius o Fahrenheit va a ser `FRIO`, `TEMPLADO` o `CALIDO`. Consideré como
alternativa guardarme la temperatura posta y que las `temperaturasAptas` sean un
rango de valores, pero me pareció una solución demasiado compleja.

Agregué métodos factory para poder realizar ambas conversiones:

```java
enum Temperatura {
  FRIO,
  TEMPLADO,
  CALIDO;

  static Temperatura ofFahrenheit(double fahrenheit) {
    return ofCelsius((fahrenheit - 32) / 1.8);
  }

  static Temperatura ofCelsius(double celsius) {
    return celsius >= 20 ? CALIDO : celsius >= 10 ? TEMPLADO : FRIO;
  }
}
```

Por su parte, a las `Prenda`s se les agrega una colección de `Temperatura`s
aptas y el mensaje `esAptaPara(temperatura)`:

```kotlin
class Prenda {
  temperaturasAptas: Temperatura[]

  fun esAptaPara(temperatura): boolean => temperaturasAptas.contains(temperatura)
}
```

Por último, el `Guardarropas` recibe el `ServicioMeteorologico` por inyección de
dependencias por constructor para luego filtrar las prendas sugeribles según el
clima:

```kotlin
class Guardarropas(servicioMeteorologico: ServicioMeteorologico, ...) {

  fun getPrendasSugeribles(categoria: Categoria): List<Prenda> => prendas
      .filter { it.esSugerible() }
      .filter { it.esAptaPara(servicioMeteorologico.getTemperatura()) }
      .filter { it.esDeCategoria(categoria) }
      .toList()
}
```

### Requerimiento 5

> Como stakeholder de QuéMePongo, quiero poder asegurar la calidad de mi
  aplicación sin incurrir en costos innecesarios.

Para que `AccuWeather` sea una única instancia y controlar la cantidad de
requests, lo hice singleton (aunque esto no me impide inyectarlo como
dependencia):

```kotlin
var guardarropas = new Guardarropas(AccuWeather.INSTANCE, ...)
```

Dentro de `AccuWeather` se "cachea" la última `Temperatura` obtenida,
actualizándose cada 12 horas.

## Cambios post Puesta en Común

### Requerimiento 5

Al final lo importante no era hacer que `AccuWeather` sea única y cachee la
última `Temperatura`, sino el hecho de que su interfaz sea sencilla para poder
mockearla, por lo que deshice el singleton.
