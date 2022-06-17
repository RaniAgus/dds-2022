# Sexta Iteración

## Solución planteada

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-6.puml)

### Requerimiento 1

> Como usuario/a de QueMePongo quiero tener una sugerencia diaria de qué ponerme
  y que todas las mañanas, diariamente, esta sea actualizada

Como la sugerencia diaria es una sola para cada usuario, muevo la lógica de 
generación de sugerencias a la clase `Usuario` y expongo el método 
`getPrendasSugeribles()` en cada `Guardarropa`.

También, agrego un atributo tipo `Atuendo` en la clase `Usuario` para almacenar
la sugerencia diaria calculada.

```java
class Usuario {
  List<Guardarropa> guardarropas;
  Atuendo sugerencia;

  void generarSugerencia(temperatura) {
    sugerencia = new Atuendo(
        sugerirPrenda(PARTE_SUPERIOR, temperatura),
        sugerirPrenda(PARTE_INFERIOR, temperatura),
        sugerirPrenda(CALZADO, temperatura),
        sugerirPrenda(ACCESORIO, temperatura)
    )
  }
  
  private void sugerirPrenda(categoria, temperatura) {
    return guardarropas
        .flatMap(it -> it.getPrendasSugeribles(categoria, temperatura))
        .atRandom()
  }
}
```


### Requerimiento 2

> Como empleado/a de QueMePongo quiero poder disparar el cálculo de sugerencias
  diarias para todos los usuarios para poder ejecutar esta acción a principio de
  cada día.

Creo una clase `RepositorioUsuarios` que contenga todos los usuarios del sistema
y un método `generarSugerencias()` que genere las sugerencias de todos los 
usuarios.

También será el encargado de tener la instancia de `ServicioMeteorologico` y
pasarle las condiciones climáticas en un objeto `Clima` para generar la
sugerencia:

```java
class RepositorioUsuarios {
  Set<Usuario> usuarios;
    
  void generarSugerencias(Temperatura temperatura) {
    usuarios.map(it -> it.generarSugerencia(temperatura));
  }
}
```

Este repositorio va a ser instanciado por una clase `GeneradorDeSugerencias` con
un método `main`, que también va a contener la instancia de 
`ServicioMeteorologico` para obtener de ahí la `Temperatura` actual:

```js
const servicioMeteorologico = new AccuWeather()
const repositorioUsuarios = new RepositorioUsuarios()

repositorioUsuarios.generarSugerencias(servicioMeteorologico.getTemperatura())
```

### Requerimiento 3

> Como usuario/a de QueMePongo, quiero poder conocer cuáles son las últimas
  alertas meteorológicas publicadas en el sistema para estar informado (pudiendo
  verlas, por ejemplo, al entrar en quemepongo.com)

Cada consulta por las condiciones climáticas hacia el `ServicioMeteorologico` se
persiste en un objeto `Clima`, que cuenta con la hora de actualización y una
colección de `Alerta` en formato `enum`:

```java
class Clima {
  Temperatura temperatura;
  Alerta[] alertas;
  LocalDateTime horaDeActualizacion;
}

enum Alerta {
  TORMENTA,
  GRANIZO
}
```

Estas consultas se guardan en un `RepositorioClima` para que luego sea posible
recuperar la última de ellas.

### Requerimiento 4

> Como empleado/a de QueMePongo, necesito poder disparar un proceso que consulte
  y actualice la lista de alertas publicadas en el sistema para tener control
  sobre cuándo se publican las mismas

Las actualizaciones en `RepositorioClima` se van actualizando con una clase 
`GeneradorDeAlertas`, la cual tiene un método `main` que consulta el clima
desde el `ServicioMeteorologico` y la agrega al repositorio:

```js
const servicioMeteorologico = new AccuWeather()
const repositorioClima = new RepositorioClima()

// ...

const clima = servicioMeteorologico.getClima()
repositorioClima.agregarActualizacion(clima)

// ...
```

### Requerimiento 5

> Como usuario/a de QuéMePongo quiero que se actualice mi sugerencia diaria con
  las condiciones climáticas actualizadas cuando se genere algún alerta durante
  el día

El método `main` de `GeneradorDeAlertas` también cuenta con una instancia del
`RepositorioUsuarios`:

```js
// ...

const repositorioUsuarios = new RepositorioUsuarios()

// ...

repositorioUsuarios.emitirAlertas(clima)
```

Al mismo se le agrega un método `emitirAlertas` que, en caso de que haya alguna
`Alerta` se emite a cada `Usuario`:

```java
class RepositorioUsuarios {
  void emitirAlertas(Clima clima) {
    if (clima.tieneAlertas()) {
      usuarios.forEach(it -> it.emitirAlerta(clima));
    }
  }
}
```

Este `Usuario` va a tener una lista de acciones a realizar ante una alerta
(a detallar más adelante). Entre ellas se encuentra `ActualizarSugerencia`, que
dispara el cálculo de sugerencias sobre el `Usuario` correspondiente:

```java
class ActualizarSugerencia implements Accion {
  void emitirA(Usuario usuario, Clima clima) {
    usuario.generarSugerencia(clima.getTemperatura());
  }
}
```

### Requerimiento 6

> Como usuario/a de QueMePongo quiero tener la posibilidad de que ante una
  alerta de tormenta la app me notifique que debo llevarme también un paraguas

Se crea otro tipo de `Accion` llamado `NotificarTormenta`, el cual va a hacer
uso del `NotificationService` en caso de que el clima tenga alerta de 
`TORMENTA`:

```java
class NotificarTormenta implements Accion {
  notificationService
  
  void emitirA(Usuario usuario, Clima clima) {
    if (clima.tieneAlerta(TORMENTA)) {
      notificationService.notify(
          "¡Alerta de tormenta! No te olvides de salir con un paraguas.");
    }
  }
}
```

### Requerimiento 7

> Como usuario/a de QueMePongo quiero que ante una alerta meteorológica de
  granizo la app me notifique que evite salir en auto

Se crea otro tipo de `Accion` llamado `NotificarGranizo`, el cual va a hacer
uso del `NotificationService` en caso de que el clima tenga alerta de
`GRANIZO`:

```java
class NotificarGranizo implements Accion {
  notificationService

  void emitirA(Usuario usuario, Clima clima) {
    if (clima.tieneAlerta(GRANIZO)) {
      notificationService.notify(
          "¡Alerta de granizo! Recomendamos evitar salir en auto.");
    }
  }
}
```

### Requerimiento 8

> Como usuario/a de QueMePongo quiero poder recibir un mail avisándome si se
  generó algún alerta meteorológico y cuál

Se crea otro tipo de `Accion` llamado `EnviarMailAlerta`, el cual va a hacer
uso del `MailSender` para notificar sobre las alertas:

```java
class EnviarMailAlerta implements Accion {
  mailSender

  void emitirA(Usuario usuario, Clima clima) {
    mailSender.send(usuario.getEmail(), clima.getAlertas().join(", "));
  }
}
```

### Requerimiento 9

> Como usuario/a de QuéMePongo quiero poder configurar cuáles de estas acciones
  (notificaciones, mail, recálculo) quiero que se ejecuten y cuáles no, además
  de soportar nuevas acciones a futuro. (No nos interesará, sin embargo,
  soportar nuevas alertas)

Todas estas acciones mencionadas implementan la interfaz `Accion` y cada 
`Usuario` va a tener solamente las que quiera configurar. Se emiten invocando al
método `emitirAlerta(clima)` previamente mencionado:

```java
interface Accion {
  emitirA(usuario, clima)
}

class Usuario {
  Accion[] accion

  void emitirAlerta(Clima clima) {
    acciones.forEach(accion -> accion.emitirA(this, clima));
  }
}
```


### Bonus 1

> Como administrador/a de QueMePongo, quiero que las sugerencias diarias se
  calculen automáticamente sin que un empleado necesite disparar esta acción
  manualmente

### Bonus 2

> Como administrador/a de QueMePongo, quiero que las alertas se publiquen en el
  sitio automáticamente sin que un empleado necesite disparar esta acción
  manualmente

<!--
## Cambios post Puesta en Común

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-N-cambios.puml)

### Requerimiento 1

### Requerimiento 2

...

### Requerimiento N
-->
