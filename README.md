# Notesbox

> Enunciado:
> [[DDS] [Objetos] Recuperatorio Diciembre 2021 - Notesbox](https://docs.google.com/document/u/1/d/e/2PACX-1vS3smHqEAmQvr-tKnUJU55y08jnIKGIksmZ0YZxd7wmJSLNOiuYOLKL8QHuSYbNH2PMIgnV1PFvU7mu/pub)

Notesbox es una pequeña empresa familiar que se dedica desde hace algunos años a
la comercialización de cajitas musicales: cuentan con un catálogo de melodías
conocidas para las que ya tienen el diseño de sus cilindros[^1] y a pedido de
sus clientes envían a fabricar y ensamblar las mismas a los talleres que estén
disponibles en ese momento. Pero hoy se acercaron a 2Diseños para contarles que
quieren ampliar su negocio e incorporar cajitas personalizadas. Y para eso van a
necesitar crear un sitio Web e informatizar (un poco) sus procesos.

## Contexto

### El sitio

El sitio de Notesbox permitirá que cualquier persona pueda ordenar cajas de
música navegando su catálogo de melodías o subiendo un fragmento de audio. Para
ello, deberá crear una cuenta, cargar su dirección y comenzar a crear su orden.
Una orden puede tener una melodía de catálogo, identificadas por un nombre y con
un precio predefinido; o una personalizada, la cual deberá ser validadas y cuyo
precio será dependiente de la longitud de la melodía final.

### Validación de las secuencias

No cualquier melodía sonará bien cuando se convierta en caja musical.
Obviamente, las melodías de catálogo ya fueron revisadas por el equipo de
Notesbox, pero las personalizadas deben pasar por un componente que extraiga del
audio la melodía principal y genere una secuencia MIDI[^2] que además sea
compatible con las posibilidades físicas de la cajita. Este componente se
desarrollará por otro equipo, queda fuera del alcance de este ~~parcial~~
proyecto su implementación, y tendrá la siguiente interfaz:

```ts
interface MIDIConverter {
  convert(melody: byte[]): string
}
```

El mensaje `convert(..)` recibe una melodía compleja y devuelve un string el
cual contendrá el MIDI. Tené en cuenta que este proceso puede demorar varios
minutos.

Una vez convertidas, un usuario podrá escuchar las melodías que aún estén
aguardando validación y marcar las mismas como válidas -si suenan bien-, para
proseguir con la orden, o inválidas, en cuyo caso se deberá informar al cliente
por mail de que la misma fue rechazada.

### Tiempos estimados

El tiempo que le queda a una orden depende principalmente de su estado. Se
estima que la validación de una orden personalizada tarda 2 días. Para todas las
órdenes, el tiempo estimado de fabricación dependerá del fabricante. Finalmente,
el tiempo de entrega estimado será de 15 días para las órdenes personalizadas
mientras que sólo tomará 5 días para las de catálogo.

### Fabricantes

Se los contacta por teléfono tanto para actualizar sus datos como para
comunicarles las órdenes pendientes.

## Requerimientos detallados

1. Como cliente, poder registrarme cargando los datos básicos de contacto
   (nombre, apellido, email) y dirección de recepción de las órdenes.
2. Como cliente, poder consultar el catálogo de melodías y ordenar una o más
   cajitas musicales para la dirección actual del cliente.
3. Como cliente, poder consultar el estado de una orden que realicé.
4. Como cliente, poder consultar el tiempo estimado que queda hasta que la orden
   llegue a mi dirección.
5. Como cliente, poder consultar la dirección a la que llegó o llegará una orden
   que realicé, independientemente de cuál sea mi dirección actual
6. Como persona administradora, poder dar de alta fábricas de cajas musicales en
   el sistema, y actualizarlas cuando sea necesario.
7. Como persona administradora, poder consultar cuales son las fábricas que
   podrían aceptar una orden (es decir, que no supere la capacidad de producción
   de la fábrica).
8. Como persona administradora, poder revisar (típicamente una o dos veces al
   día) las órdenes pendientes y asignarles una fábrica a su elección que esté
   en condiciones de aceptar la orden
9. Como persona administradora, poder consultar todas las órdenes asignadas a
   una fábrica en un determinado período.
10. Como cliente, poder realizar una orden personalizada, subiendo el fragmento
    de audio.
11. Como cliente, poder saber el precio de una orden, el cual se calcula de
    forma diferente si es de una orden de catálogo o personalizada.
12. Como cliente, poder consultar si la orden personalizada fue aceptada o
    rechazada, y recibir una notificación con esta misma información en cuanto
    el conversor de secuencias termine de ejecutarse. Debe soportar múltiples
    formas de notificación; se cuenta entre ellas con el siguiente componente:

```js
interface MailSender {
  send(address, subject, body)
}
```

1.  Como persona administradora, poder listar todas las órdenes,
    independientemente de su tipo y de si la conversión o validación terminó o
    no.

[^1]:
    El cilindro es un componente metálico de una caja de música que contiene una
    muesca por cada nota, y su forma exacta por tanto variará para cada melodía.

[^2]:
    Una secuencia MIDI es un archivo que, en su forma más simple, contiene el
    listado de notas y silencios de una pieza musical, para nosotros un String.
