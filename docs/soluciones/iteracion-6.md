# Sexta Iteración

## Solución planteada (c/cambios post puesta en común)

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

  void generarSugerencia() {
    sugerencia = new Atuendo(
        sugerirPrenda(PARTE_SUPERIOR),
        sugerirPrenda(PARTE_INFERIOR),
        sugerirPrenda(CALZADO),
        sugerirPrenda(ACCESORIO)
    )
  }
  
  private void sugerirPrenda(categoria) {
    return guardarropas
        .flatMap(it -> it.getPrendasSugeribles(categoria))
        .atRandom()
  }
}
```


### Requerimiento 2

> Como empleado/a de QueMePongo quiero poder disparar el cálculo de sugerencias
  diarias para todos los usuarios para poder ejecutar esta acción a principio de
  cada día.

Creo una clase `GenerarSugerencias` que cuente con todos los usuarios del 
sistema a través de un `RepositorioUsuarios` y que al ejecutarse invoque para
todos el método `generarSugerencia()`:

```ts
class GenerarSugerencias implements TareaProgramada {
    usuarios: RepositorioUsuarios

    ejecutar() {
        usuarios.getAll().forEach(it => it.generarSugerencia())
    }
}
```

### Requerimiento 3

> Como usuario/a de QueMePongo, quiero poder conocer cuáles son las últimas
  alertas meteorológicas publicadas en el sistema para estar informado (pudiendo
  verlas, por ejemplo, al entrar en quemepongo.com)

Cada consulta por las condiciones climáticas hacia el `ServicioMeteorologico` se
persiste en una colección de `Alerta`s en formato `enum`:

```java
enum Alerta {
  TORMENTA,
  GRANIZO
}
```

Estas consultas se guardan en un `RepositorioAlertas` para que luego sea posible
recuperar las últimas en ser consultadas.

### Requerimiento 4

> Como empleado/a de QueMePongo, necesito poder disparar un proceso que consulte
  y actualice la lista de alertas publicadas en el sistema para tener control
  sobre cuándo se publican las mismas

Las `Alertas` en `RepositorioAlertas` se van actualizando a través de una tarea
programada `GenerarAlertas`, la cual también cuenta con un método `ejecutar()` 
que consulta con el `ServicioMeteorologico`:

```ts
class GenerarAlertas implements TareaProgramada {
    alertas: RepositorioAlertas
    servicioMeteorologico: ServicioMeteorologico
    //...

    ejecutar() {
        const nuevasAlertas = alertas.actualizar(servicioMeteorologico.getAlertas())
        //...
    }
}
```

### Requerimiento 5

> Como usuario/a de QuéMePongo quiero que se actualice mi sugerencia diaria con
  las condiciones climáticas actualizadas cuando se genere algún alerta durante
  el día

El método `actualizar()` del `RepositorioAlertas` devuelve cuáles de todas las 
alertas son nuevas, para que en caso de que continúe un alerta existente no se
vuelva a notificar a los usuarios.

El método `ejecutar()` de `GenerarAlertas` también cuenta con una instancia del
`RepositorioUsuarios`:

```js
class GenerarAlertas implements TareaProgramada {
    alertas: RepositorioAlertas
    servicioMeteorologico: ServicioMeteorologico
    usuarios: RepositorioUsuarios

    ejecutar() {
        const nuevasAlertas = alertas.actualizar(servicioMeteorologico.getAlertas())
        usuarios.getAll().forEach(it => it.generarSugerencia())
    }
}
```

Cada `Usuario` va a tener una lista de acciones a realizar ante una alerta
(a detallar más adelante). Entre ellas se encuentra `ActualizarSugerencia` que
dispara el cálculo de sugerencias sobre el `Usuario` correspondiente:

```java
class ActualizarSugerencia implements AccionTrasAlertas {
  void anteNuevasAlertas(Usuario usuario, Alerta[] _) {
    usuario.generarSugerencia();
  }
}
```

### Requerimiento 6 y 7

> Como usuario/a de QueMePongo quiero tener la posibilidad de que ante una
  alerta de tormenta la app me notifique que debo llevarme también un paraguas

> Como usuario/a de QueMePongo quiero que ante una alerta meteorológica de
granizo la app me notifique que evite salir en auto

Se crea otro tipo de `AccionTrasAlertas` llamado `NotificarAlertas`, el cual va
a hacer uso del `NotificationService` y va a enviar la notificación
correspondiente:

```ts
class NotificarTormenta implements Accion {
  notificationService
  mensajes: { [s: Alerta]: string }
  
  void anteNuevasAlertas(Usuario usuario, Alerta[] alertas) {
    alertas.forEach(it -> notificador.enviarMensaje(usuario, mensajes[it]));
  }
}
```

Los mensajes para cada tipo de `Alerta` son almacenados en un
`Map<Alerta, String>` para aprovechar el paradigma de objetos y poder soportar
internacionalización.

### Requerimiento 8

> Como usuario/a de QueMePongo quiero poder recibir un mail avisándome si se
  generó algún alerta meteorológico y cuál

Se crea otro tipo de `AccionTrasAlertas` llamado `EnviarMailAlerta`, el cual va
a hacer uso de un `Mailer` para notificar sobre las alertas:

```java
class EnviarMailAlerta implements Accion {
  mailer

  void emitirA(Usuario usuario, Alerta[] alertas) {
    mailer.send(usuario.getEmail(), alertas);
  }
}
```

Este `Mailer` es una adaptación del `MailSender` con una interfaz más amigable:

```java
interface Mailer {
  enviarAlertas(String email, Set<Alerta> alertas);
}
```

### Requerimiento 9

> Como usuario/a de QuéMePongo quiero poder configurar cuáles de estas acciones
  (notificaciones, mail, recálculo) quiero que se ejecuten y cuáles no, además
  de soportar nuevas acciones a futuro. (No nos interesará, sin embargo,
  soportar nuevas alertas)

Todas estas acciones mencionadas implementan la interfaz `AccionTrasAlertas` y 
cada `Usuario` va a tener solamente las que quiera configurar. Se emiten 
invocando al método `emitirAlertas(alertas)` previamente mencionado:

```java
interface Accion {
  accionTrasAlertas(usuario, alertas)
}

class Usuario {
  AccionTrasAlertas[] acciones

  void emitirAlerta(Alerta[] alertas) {
    acciones.forEach(accion -> accion.emitirA(this, alertas));
  }
}
```


### Bonus 1 y 2

> Como administrador/a de QueMePongo, quiero que las sugerencias diarias se
  calculen automáticamente sin que un empleado necesite disparar esta acción
  manualmente

> Como administrador/a de QueMePongo, quiero que las alertas se publiquen en el
  sitio automáticamente sin que un empleado necesite disparar esta acción
  manualmente

Ambos `GenerarAlertas` y `GenerarSugerencias` implementan la interfaz 
`TareaProgramada`, que solamente cuenta con el método `ejecutar()`.

En un método `main`, estas tareas programadas se van almacenando en un 
`Planificador` junto con una expresión cron que indique la frecuencia 
con la cual se van a ejecutar:

```ts
const planificador = new Planificador()
    // Las alertas se generan cada 3 horas
    .agregarTarea(new GenerarAlertas(...), "0 0 0/3 * * ?")
    // Las sugerencias se generan todos los días a las 4AM
    .agregarTarea(new GenerarSugerencias(...), "0 0 4 * * ?")
```

Internamente, este `Planificador` utiliza la biblioteca Quartz, la cual soporta
este tipo de expresiones cron para planificar tareas internamente en la
aplicación.

Más adelante en el `main`, este planificador es iniciado, manteniendo la 
ejecución de la aplicación "infinitamente":

```ts
planificador.iniciar()
```

Opté por la planificación interna ya que es más fácil de implementar: se puede 
lograr desde Java sin ninguna configuración en el servidor donde se va a 
desplegar la aplicación.

Como esta solución puede ser menos robusta, contaría con algún servicio externo
que reinicie la aplicación inmediatamente ante caídas como Docker o PM2.
