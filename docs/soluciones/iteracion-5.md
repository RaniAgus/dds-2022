# Quinta Iteración

## Solución planteada

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-5.puml)

### Requerimiento 1
> Como usuario/a de QuéMePongo, quiero poder manejar varios guardarropas para
separar mis prendas según diversos criterios (ropa de viaje, ropa de
entrecasa, etc).

Se crea una clase `Usuario`, la cual tiene sus propios `Guardarropas`:
```ts
class Usuario {
    guardarropas: Guardarropas[]
}
```

### Requerimiento 2
> Como usuario/a de QuéMePongo, quiero poder crear guardarropas compartidos con
otros usuarios/as (ej, ropa que comparto con mi hermano/a).

Se puede agregar una misma instancia de `Guardarropas` a distintos `Usuarios`:

```ts
let persona1 = new Usuario(...)
let persona2 = new Usuario(...)

// ...

let guardarropasCompartido = new Guardarropas(...)
persona1.agregar(guardarropasCompartido)
persona2.agregar(guardarropasCompartido)
```

### Requerimiento 3
> Como usuario/a de QuéMePongo, quiero que otro usuario/a me proponga
tentativamente agregar una prenda al guardarropas.

Se crea una clase `SolicitudAgregar` que se puede agregar al `Guardarropas`:

```ts
let solicitud = new SolicitudAgregar(prenda)
guardarropas.solicitar(solicitud)
```

La solicitud arranca en estado `EstadoModificacion.PENDIENTE` y al aceptarse en
un `Guardarropas` se agrega la `Prenda` al mismo:

```ts
class SolicitudAgregar {
    prenda: Prenda
    estado = EstadoModificacion.PENDIENTE
    
    aceptarEn(guardarropas: Guardarropas) {
        guardarropas.agregar(prenda)
        this.estado = EstadoModificacion.ACEPTADA
    }
}
```

### Requerimiento 4
> Como usuario/a de QuéMePongo, quiero que otro usuario/a me proponga
tentativamente quitar una prenda del guardarropas.

Como ambas operaciones (`agregar()` y `quitar()`) son iguales en cuanto a su 
estado pero distintas en cuanto a la acción a realizar, se extrae la lógica en
común a una `SolicitudModificacion` abstracta:

```ts
class Guardarropas {
    solicitudes: SolicitudModificacion[]
    
    solicitar(solicitud) {
        solicitudes.add(solicitud)
    }
}

abstract class SolicitudModificacion {
    estado = EstadoModificacion.PENDIENTE

    aceptarEn(guardarropas: Guardarropas) {
        this.realizarEn(guardarropas)
        this.estado = EstadoModificacion.ACEPTADA
    }
    
    abstract realizarEn(guardarropas: Guardarropas)
}
```

Que, en el caso de `SolicitudQuitar`, se quita la `Prenda`:

```ts
class SolicitudQuitar extends SolicitudModificacion {
    prenda: Prenda
    
    override realizarEn(guardarropas: Guardarropas) {
        guardarropas.quitar(prenda)
    }
}
```

### Requerimiento 5
> Como usuario/a de QuéMePongo, necesito ver todas las propuestas de
modificación (agregar o quitar prendas) del guardarropas y poder aceptarlas o
rechazarlas.

El `Guardarropas` incluye un método `getSolicitudesPendientes()` que aplica un
filtro para obtener las pendientes:

```kotlin
class Guardarropas {
    solicitudes: SolicitudModificacion[]
    
    getSolicitudesPendientes() {
        return solicitudes.filter { it.estaEnEstado(PENDIENTE) }
    }
}
```

Para aceptar una, se envía un `aceptarEn(guardarropas)`. Para rechazarla, se 
envía un `rechazar()` que simplemente cambia el estado para que no vuelva a
aparecer en el anterior filtro:

```ts
abstract class SolicitudModificacion {
    estado = EstadoModificacion.PENDIENTE

    rechazar() {
        this.estado = EstadoModificacion.RECHAZADA
    }
}
```

### Requerimiento 6
> Como usuario/a de QuéMePongo, quiero poder deshacer las propuestas de
modificación que haya aceptado.

Todas las `SolicitudModificacion` implementan un mensaje `revertirEn()` que es
utilizado por el método `deshacerEn()` para revertir la acción realizada. 
Además, se cambia el estado a `RECHAZADA` para que no vuelva a aparecer para
revertir:

```ts
abstract class SolicitudModificacion {
    estado = EstadoModificacion.PENDIENTE

    deshacerEn(guardarropas: Guardarropas) {
        this.revertirEn(guardarropas)
        this.estado = EstadoModificacion.RECHAZADA
    }
    
    abstract revertirEn(guardarropas: Guardarropas)
}
```


<!--
## Cambios post Puesta en Común

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-N-cambios.puml)

### Requerimiento 1

### Requerimiento 2

...

### Requerimiento N
-->
