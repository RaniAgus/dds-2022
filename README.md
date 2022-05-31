# Noodle
> Enunciado:
> [Noodle - Primer Parcial](https://docs.google.com/document/d/e/2PACX-1vR1mib70_YmKeh3G8J-hRoQFvGMKB0PE6ks0OO9-7GwO0Y3pCyYrDs2dJUNR90D8AYXg_AAue1-tQTC/pub)

En estos tiempos de pandemia y clases remotas, contar con una herramienta para
la correcta gestión de aulas virtuales es esencial. Por eso es que Tío Molinas,
una conocida marca de galletitas y pastas secas en busca de diversificación ha
decidido encargar a 2Diseños la creación de un nuevo software para entornos
educativos, totalmente innovador y para nada parecido a Moodle: Noodle.

## Contexto

Según nos cuenta la gente de Tío Molinas, la primera iteración de Noodle
consistirá en el Módulo de Grupos: necesitan una aplicación que le permita a une
docente gestionar el armado de grupos de estudiantes y sus integrantes, así como
las asignaciones que les son dadas (exámenes, trabajos prácticos, etc).

### Integrantes

En primer lugar, nos comentan que el sistema deberá permitirle al docente
configurar un tamaño ideal para los grupos, y una vez alcanzado el grupo podrá
cerrarse y comenzar a recibir asignaciones.

Existen casos donde el tamaño ideal puede ser ignorado por el docente (por
ejemplo, porque el tamaño ideal no es divisor exacto de la cantidad de
estudiantes del curso). En estos casos, el docente podrá cerrar al grupo cerrado
ignorando este tamaño, pero ello deberá ser aprobado por un segundo docente. De
igual forma, una vez que el grupo es finalmente cerrado, cualquier adición o
remoción de integrantes también debe ser aprobada por otro docente.

En Tío Molinas nos comentan que tal vez quieran permitir a futuro otros cambios
más allá de recibir o perder integrantes y ser cerrado. Por ejemplo, quizás les
interesaría eliminar un grupo o volver a abrirlo, si es que las pruebas de
usuario muestran que esta necesidad es frecuente, aunque no están seguros de que
finalmente suceda.

### Tareas

Durante la vida de un grupo, hay diferentes tareas asociadas a los cambios que
este sufra. En concreto, al cerrar un grupo se desea crear un repositorio en el
sitio Guitab.com dándole acceso a todos sus miembros. Del mismo modo, cada vez
que un integrante se inscriba o dé de baja de un grupo, se debe notificar a
todos los integrantes existentes del mismo por mail. Si el grupo ya estaba
cerrado, se deberá, además, dar o quitar permisos a dicho integrante del
repositorio de Guitab.

Estas tareas deben ser configurables por un administrador y es probable que
varíen mucho a futuro. Por ejemplo, se está pensando darlos de alta en un excel,
notificar al docente asignado, darles la primera asignación automáticamente,
etc.

### Asignaciones

Finalmente, nuestra aplicación deberá ser capaz de darle acceso a los distintos
grupos a diferentes asignaciones. Por el momento, las asignaciones son solo
Trabajos Prácticos y Actividades de Clase.

Las asignaciones se identifican mediante un título, y pueden tener una o varias
entregas semanales. Cada entrega está enlazada mediante una URL que se le
comparte a los estudiantes.

Para facilitar el trabajo docente, se desea automatizar el dar acceso a
diferentes entregas semanales. Esto implica que, si una asignación tiene varias
entregas, una vez por semana deberán ser habilitadas, en orden, para los
diferentes grupos cerrados, y cada integrante del grupo deberá recibir un mail.

## Requerimientos detallados

1. Como docente, deseo poder crear N grupos de trabajo de un tamaño ideal M,
   vacíos, para permitir la inscripción de estudiantes a cada grupo
2. Como estudiante, deseo poder inscribirme o darme de baja instantáneamente de
   un grupo abierto.
3. Como estudiante, deseo recibir una notificación por correo electrónico cada
   vez que alguien se inscribe o se da de baja de mi grupo de trabajo.
4. Como docente, deseo poder cerrar un grupo que tiene tamaño M.
5. Como docente, deseo que cuando un grupo sea cerrado, se cree el repositorio
   correspondiente en Guitab.
6. Como docente, deseo poder aprobar o rechazar el intento de otro docente de
   cerrar un grupo que no es de tamaño M.
7. Como estudiante, quiero solicitar inscribirme o darme de baja de un grupo
   cerrado.
8. Como docente, quiero poder aprobar o rechazar la solicitud de alta o baja de
   un estudiante para un grupo cerrado.
9. Como docente, deseo que al incluir o eliminar un estudiante de un grupo
   cerrado, este reciba o pierda los accesos al repositorio del equipo.
10. Como administrador, deseo poder configurar qué tareas se ejecutan cuando se
    realiza un cambio sobre un grupo.
11. Como docente, quiero poder crear asignaciones con una o varias entregas.
12. Como docente, deseo que el sistema le dé automáticamente acceso a los
    estudiantes a cada entrega a medida que las semanas de cursada transcurren

Se cuenta con el SDK del sitio Guitab, el cual expone la siguiente interfaz:

```ts
interface GuitabSDK {
  crearRepositorioConAccesos(nombre: string, usernames: string[])
  darAcceso(nombreRepo: string, username: string)
  quitarAcceso(nombreRepo: string, username: string)
}
```

Asimismo, para enviar mails se cuenta con la siguiente biblioteca:

```js
interface MailSender {
  send(address, subject, body)
}
```
