# Primera Iteración

## Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-1.puml)

## Solución

### Requerimiento 1

> 1. Como usuario/a de QuéMePongo, quiero especificar qué tipo de prenda estoy
>    cargando (zapatos, camisa de mangas cortas, pantalón, etc).

Todas las instancias de `Prenda` incluyen un atributo `Tipo`, que internamente
está modelado como un `String`. Lo definí de esta forma porque me permite cargar
dinámicamente nuevos tipos de `Prenda`, lo cual no puede hacerse con un `enum`:

```ts
class Prenda {
  tipo: Tipo
}

class Tipo {
  nombre: String
}
```

### Requerimiento 2

> 2. Como usuario/a de QuéMePongo, quiero identificar a qué categoría pertenece
>    una prenda (parte superior, calzado, parte inferior, accesorios).

Todas las instancias de `Prenda` implementan un método `getCategoria()`, que
devuelve una instancia de `Categoria`. Lo definí con un `enum`, ya que permite
implementar un número limitado de valores posibles.

```ts
class Prenda {
  getCategoria(): Categoria {
    // ...
  }
}

enum Categoria {
  CALZADO,
  PARTE_SUPERIOR,
  PARTE_INFERIOR,
  ACCESORIO
}
```

### Requerimiento 3

> 3. Como usuario/a de QuéMePongo, quiero poder indicar de qué tela o material
>    está hecha una prenda

Hasta ahora no cuento con suficiente información para saber si debo esperar un
cierto comportamiento dependiendo del valor de la tela o si es simplemente un
atributo de la misma. Entonces, lo defino como un `String` perteneciente a una
clase `Tela`, y que ésta forme parte de la `Prenda`:

```ts
class Prenda {
  tela: Tela
}

class Tela {
  nombre: String
}
```

### Requerimientos 4 y 5

> 4. Como usuario/a de QuéMePongo, quiero poder indicar un color principal para
>    mis prendas
> 5. Como usuario/a de QuéMePongo, quiero poder indicar, si existe, un color
>    secundario para mis prendas.

Todas las instancias de `Prenda` cuentan con dos atributos de tipo `Color` para
guardar ahí su color primario y secundario. En la implementación, usaré la clase
`Color` de Java ya que incluye métodos para resolver comparaciones y casteos a
distintos formatos (RGB, CYMK, etc).

```ts
class Prenda {
  colorPrimario: Color
  colorSecundario: Color
}
```

### Requerimiento 6

> 6. Como usuario/a de QuéMePongo, quiero evitar que haya prendas cuya categoría
>    no se condiga con su tipo. (Ej, una remera no puede ser calzado).

Hice que la `Categoria` sea un atributo de `Tipo`, para que al consultar por la
categoría de una `Prenda` ésta delegue la responsabilidad de definir su
categoría en su `Tipo`:

```ts
class Prenda {
  tipo: Tipo

  getCategoria(): Categoria {
    return tipo.getCategoria()
  }
}

class Tipo {
  categoria: Categoria
}
```

### Requerimiento 7

> 7. Como usuario/a de QuéMePongo, quiero evitar que haya prendas sin tipo,
>    tela, categoría o color primario

La clase `Prenda` incluye un constructor con solamente estos atributos (recordar
que la `Categoria` está incluida en el `Tipo`). También se crea uno que incluya
todos:

```ts
class Prenda {
  constructor(tipo: Tipo, tela: Tela, colorPrimario: Color) {
    //...
  }
  constructor(tipo: Tipo, tela: Tela, colorPrimario: Color, colorSecundario: Color) {
    //...
  }
}
```
