# Segunda Iteración

## Solución planteada

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-2.puml)

### Requerimiento 1

> Como usuario/a de QuéMePongo, quiero especificar qué trama tiene la tela de
  una prenda (lisa, rayada, con lunares, a cuadros o un estampado).

A cada `Prenda` se le agrega un atributo `Trama`, que es un `enum` con todos 
tipos de trama posibles:

```ts
class Prenda {
  trama: Trama
}

enum Trama {
  LISA,
  RAYADA,
  LUNARES,
  CUADROS,
  ESTAMPADO
}
```
En un principio consideré agregarlo como atributo a la `Tela`, pero no le 
agrega ningún valor ya que cualquier tela se puede combinar con cualquier trama.

### Requerimiento 4

> Como usuario/a de QuéMePongo, quiero guardar un borrador de la la última
prenda que empecé a cargar para continuar después.

Se crea una clase `Borrador` encargada de crear una `Prenda` y mantener en 
su estado interno borradores de la misma:

```ts
class Borrador {
    tipo
    tela
    trama
    // ... etc
    
    crearPrenda(): Prenda {
        return new Prenda(...)
    }
}

```

### Requerimiento 2

> Como usuario/a de QuéMePongo, quiero crear una prenda especificando primero de
  qué tipo es.

Se incluye un parámetro `Tipo` al constructor de `Borrador`:

```ts
class Borrador {
    constructor(tipo: Tipo) { 
        // ... 
    }
}
```

### Requerimiento 3

> Como usuario/a de QuéMePongo, quiero crear una prenda especificando en segundo
  lugar los aspectos relacionados a su material (colores, material, trama, etc)
  para evitar elegir materiales inconsistentes con el tipo de prenda.

Cualquier `Borrador` va a incluir setters para configurar sus colores, trama y
materiales. 

```ts
class Borrador {
    conTrama(trama: Trama) {
      // setter   
    }
    conColorPrimario(colorPrimario: Color) {
      // setter
    }
    // ...
}
```

En el caso de la `Tela`, se delega la responsabilidad de verificar si es un 
valor válido al `Tipo` ya ingresado (el cual va a tener todos los valores de 
`Tela` posibles:

```ts
class Borrador {
     conTela(tela: Tela) {
         if (!this.tipo.esTelaValida(tela)) {
             throw new PrendaInvalidaException(...)
         }
         // ..
     }
}

```
Por lo tanto, se convierte la clase `Tela` a `enum` para dejar asentado en el 
modelo todos los valores posibles (lo cual no se puede lograr utilizando un 
`String`):

```ts
enum Tela {
  ALGODON,
  CUERO,
  LANA,
  JEAN,
  ...
}
```

### Requerimiento 5

> Como usuario/a de QuéMePongo, quiero poder no indicar ninguna trama para una
  tela, y que por defecto ésta sea lisa.

El método `crearPrenda` de un `Borrador` va a incluir ese valor por defecto en
caso de que sea `null`:

```ts
class Borrador {
    crearPrenda() {
        return new Prenda(
            // ...
            this.tela != null ? this.tela : Tela.LISA, 
            // ...
        )
    }
}
```

### Requerimiento 6

> Como usuario/a de QuéMePongo, quiero poder guardar una prenda solamente si
  esta es válida.

Tanto en en los setters como en el método `crearPrenda` se agregan validaciones 
para que los atributos requeridos no sean nulos:

```ts
class Borrador {
  conColorPrimario(colorPrimario: Color) {
     if (colorPrimario == null) {
         throw new PrendaInvalidaException(...)
     }
     // ...
  }

  crearPrenda() {
    return new Prenda(
        // ...
        this.colorPrimario.orElseThrow(() => new PrendaInvalidaException(...)),
        // ...
    )
  }
}
```

Por lo tanto, teniendo en cuenta los 3 requerimientos anteriores, se considera 
peligroso hacer `new` de una`Prenda` en cualquier parte del código que no sea el
método `crearPrenda` de `Borrador`, ya que esa instancia no va a contar con las 
validaciones que se incluyen en el mismo.

<!--
## Cambios post Puesta en Común

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-2-cambios.puml)

### Requerimiento 1

### Requerimiento 2

...

### Requerimiento N
-->