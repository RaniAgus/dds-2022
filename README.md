# She-Ra

> Enunciado:
> [[DDS] Segundo Recuperatorio Primer Parcial DDS 2021: She-Ra](https://docs.google.com/document/d/1XXPL3ma0Uj8tLCvAMUkKTl9sC45JfzqWOTcwRcA39Fw/edit)

Grayskullssian, reconocida Software Factory, está emprendiendo el desarrollo de
She-Ra: una herramienta que revolucionará la administración de tareas, errores e
incidentes (issues) en proyectos de software.

## Contexto

### Tareas

She-Ra es una plataforma que permitirá que quienes la usen carguen diferentes
tareas relacionadas al desarrollo de software. Las Tareas pueden ser: Historias
(requerimientos nuevos), Fallos (errores resultantes de las pruebas) o Épicas
(Funcionalidades grandes del sistema que pueden dividirse en pedazos más
pequeños). Por ejemplo, una Épica podría ser "Administración de Contratos", una
Historia "Como Usuario deseo crear un contrato" y un fallo "Creación de
contratos no guarda el precio".

Una Tarea siempre tiene un nombre y una descripción y es parte de un proyecto.
Además, las Historias y Fallos contienen una estimación en días o puntos, una
persona asignada, opcionalmente una imagen (URL externa) y pueden estar
asociadas a una Iteración, que es el período de tiempo en el cual se planifica
hacerlas.

A su vez, las Épicas engloban Historias y Fallos en unidades conceptuales. Una
Épica puede estar compuesta por un sinnúmero de subtareas, que pueden ser
historias, fallos, u otras épicas (que a su vez pueden tener subtareas de
cualquier tipo y así sucesivamente).

Finalmente, una tarea puede estar en uno de varios estados, a saber: Pendiente,
Por Hacer, En Progreso, o Terminada.

### Asignaciones

Tener una tarea asignada implica trabajo por hacer. Es por esto que se desea
notificar a las personas en sus teléfonos cuando una Tarea (historia o fallo) se
les asigna.

Para ello, la plataforma sobre la que se construye SheRa incorpora la siguiente
biblioteca externa de notificaciones:

```ts
interface Notifier {
  notify(email: string, message: string): void;
}
```

No se planea soportar diversos medios de notificación, pero sí cabe aclarar que
esta biblioteca se encuentra en fase _beta_, por lo cual tanto su implementación
como interfaz pueden cambiar en los próximos meses. Estimaciones y Validaciones

Para poder pasar al estado "Por Hacer", una tarea (historia o fallo) debe estar
lista. Esto significa que el _Product Owner (PO)_ (el rol encargado de
administrar todas las tareas) haya validado que la tarea esté completa, y que la
estimación sea razonable.

Mientras una tarea está "Pendiente", alguien del equipo de desarrollo podrá
ingresar una estimación. Esta estimación puede ser aprobada o rechazada por
quien es PO. Si es rechazada, se descarta y quien es PO puede reescribir la
tarea con mayor detalle para lograr una estimación más adecuada. Entonces
alguien del equipo podrá reestimar la tarea, que nuevamente quedará pendiente de
ser aprobada por quien es _PO_.

Una vez que la estimación es aprobada, se vuelve definitiva. Recién entonces
quién es _PO_ podrá marcar la tarea como "Por Hacer".

Por último, para agilizar el proceso, se desea que todas las tareas que hayan
estado Pendientes y estimadas por más de un mes aproximadamente, sean
automáticamente marcadas como "Por Hacer".

## Requerimientos Detallados

1. Como Product Owner, deseo poder crear un proyecto, indicando su nombre.
2. Como Product Owner, deseo poder cargar una Tarea especificando su tipo,
   nombre, descripción, proyecto al que pertenece e imagen si es una Historia o
   Fallo.
3. Como Product Owner, deseo poder asociar subtareas (historias, fallos o
   subépicas) a una Épica.
4. **Como Product Owner, deseo poder ver el listado plano de todas las Historias
   o Fallos asociadas a una Épica, ya sea directamente o a través de
   subépicas.**
5. Como gerente de proyecto, deseo poder crear una Iteración, especificando su
   fecha de inicio y de fin.
6. Como gerente de proyecto, deseo poder asociar una Tarea (historia o fallo) a
   una Iteración
7. Como gerente de proyecto, deseo poder ver todas las tareas planeadas para una
   Iteración
8. Como gerente de proyecto, deseo conocer el estado de cada Tarea.
9. Como integrante del equipo, deseo poder asignar una tarea (historia o fallo)
   a otra persona.
10. **Como integrante del equipo, deseo recibir una notificación cuando una
    Tarea me es asignada**
11. Como integrante del equipo, deseo poder agregar una estimación tentativa a
    una Historia o Fallo.
12. Como PO, deseo evitar que una Historia o Fallo se marque "Por Hacer" si no
    tiene una estimación definitiva.
13. **Como PO deseo rechazar una estimación de una Historia o Fallo**
14. **Como PO deseo aceptar una estimación de una Historia o Fallo y que esta
    sea definitiva**
15. Como PO deseo poder marcar una historia como "Por Hacer"
16. **Como PO deseo que las tareas que hayan estado Pendientes por más de un mes
    y tengan una estimación definitiva, se marquen "Por hacer" de forma
    automática.**
