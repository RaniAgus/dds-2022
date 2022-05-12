# Tercera Iteración

## Solución planteada

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-3.puml)

### Requerimiento 1

> Como usuario/a de QuéMePongo, quiero recibir una sugerencia de prendas que me
> vista completamente (torso, piernas y pies).

Para modelar un atuendo sugerido, generalicé la clase `Uniforme`
renombrándola a `Atuendo`. En otras palabras, un uniforme sería un caso
particular de `Atuendo`.

### Requerimiento 2

> Como usuario/a de QuéMePongo, quiero que una sugerencia pueda incluir
> accesorios (guantes, gorros, bufandas, anteojos de sol, etc).

Como una sugerencia puede o no incluir accesorios, agregué ese atributo a la
clase `Atuendo`.

### Requerimiento 3

> Como usuario/a de QuéMePongo, quiero recibir varias sugerencias que combinen
> las prendas de mi guardarropas

Modelo el `Guardarropas` como una colección de `Prendas` que puede tener un
usuario del sistema.

### Requerimiento 4

> Como usuario/a de QuéMePongo, quiero que una sugerencia cubra cada parte del
> cuerpo con no más de una prenda, es decir, evitando superposiciones.

Quien instancie la clase `Atuendo` deberá respetar el orden de los parámetros
que viene dado en el constructor. No agrego validaciones porque decido confiar
en que las sugerencias generadas van a provenir del sistema y van a ser
consistentes.

### Requerimiento 5

> Como usuario/a QueMePongo, quiero que al aparecer en una sugerencia, la
> aplicación asuma que una prenda ha sido usada.

Se le agrega a la `Prenda` un método `usar()` que actualice su `Estado`:

```ts
class Prenda {
  estado: Estado

  usar() {
    estado = estado.usar()
  }
}
```

Cada `Estado` se encarga de actualizarse y cambiarse de estado según una
determinada acción:

```ts
interface Estado {
  usar(): Estado
}

class Nueva implements Estado {
  usar() => new Usada()
}
```

### Requerimiento 6

> Como usuario/a QueMePongo, quiero que luego de ser usada 2 veces, se considere
> a una prenda como "sucia"

Al segundo uso, la `Prenda` pasa a estado `Sucia`:
```ts
class Usada implements Estado {
  usar() => new Sucia()
}
```

### Requerimiento 7

> Como usuario/a de QuéMePongo, quiero que una prenda "sucia" pueda usarse hasta
> 3 veces para luego ser considerada "percudida".

El estado `Sucia` tiene un contador de usos:
```ts
class Sucia implements Estado {
  usos = 0;

  usar() => (++usos > 3 ? new Percudida() : this);
}
```

### Requerimiento 8

> Como usuario/a de QuéMePongo, quiero indicar que una prenda ha sido puesta a
> lavar.

La clase `Prenda` tiene un atributo `lavandose` como boolean:

```ts
class Prenda {
  lavandose: boolean

  ponerALavar() {
    lavandose = true
  }
}
```

### Requerimiento 9

> Como usuario/a de QuéMePongo, quiero que una prenda no pueda ser sugerida
> mientras está lavándose.

El método `esSugerible()` de la `Prenda` depende tanto de `lavandose` como de su
`Estado`:

```ts
class Prenda {
  esSugerible(): boolean => estado.esSugerible()
}
```

### Requerimiento 10

> Como usuario/a de QuéMePongo, quiero que una prenda "percudida" no pueda ser
> nunca más sugerida.

La clase `Prenda` cuenta con un método `esSugerible()` que se delega según su
`Estado`. En el caso de `Percudida`, este método va a devolver `false`.

```ts
class Percudida implements Estado {
  esSugerible(): boolean => false
}
```

### Requerimiento 11

> Como usuario/a de QuéMePongo, quiero que una prenda "sucia" que es lavada,
> deje de estarlo.

`Prenda` tendrá un método `lavar()`, que también se delega en su `Estado`:

```ts
class Prenda {
  lavar() {
    lavandose = false
    estado = estado.lavar()
  }
}
```

Y para el caso de `Sucia` va a devolver una nueva instancia de `Usada`:

```ts
class Sucia implements Estado {
  lavar(): Estado => new Usada()
}
```
