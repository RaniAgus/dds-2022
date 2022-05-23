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



### Requerimiento 4
> Como usuario/a de QuéMePongo, quiero que otro usuario/a me proponga
tentativamente quitar una prenda del guardarropas.



### Requerimiento 5
> Como usuario/a de QuéMePongo, necesito ver todas las propuestas de
modificación (agregar o quitar prendas) del guardarropas y poder aceptarlas o
rechazarlas.



### Requerimiento 6
> Como usuario/a de QuéMePongo, quiero poder deshacer las propuestas de
modificación que haya aceptado.



<!--
## Cambios post Puesta en Común

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-N-cambios.puml)

### Requerimiento 1

### Requerimiento 2

...

### Requerimiento N
-->
