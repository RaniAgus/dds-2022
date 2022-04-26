# Segunda Iteración

## Solución planteada

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-2.puml)

### Otros cambios

Se renombró la clase `Tela` a `Material` porque pueden existir accesorios hechos
de un material que no es un tipo de tela, por lo que me parece una mejor 
abstracción el usar ese nombre.

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
En un principio consideré agregarlo como atributo al `Material`, pero no le 
agrega ningún valor ya que cualquier material se puede combinar con cualquier 
trama.

### Requerimiento 4

> Como usuario/a de QuéMePongo, quiero guardar un borrador de la la última
prenda que empecé a cargar para continuar después.

Se crea una clase `Borrador` encargada de crear una `Prenda` y mantener en 
su estado interno borradores de la misma:

```ts
class Borrador {
    tipo
    material
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

En el caso del `Material`, se delega la responsabilidad de verificar si es un 
valor válido al `Tipo` ya ingresado (el cual va a tener todos los valores de 
`Material` posibles:

```ts
class Borrador {
     conMaterial(material: Material) {
         if (!this.tipo.esMaterialValido(material)) {
             throw new PrendaInvalidaException(...)
         }
         // ..
     }
}

```
Por lo tanto, se convierte la clase `Material` a `enum` para dejar asentado en 
el modelo todos los valores posibles (lo cual no se puede lograr utilizando un 
`String`):

```ts
enum Material {
  ALGODON,
  CUERO,
  LANA,
  JEAN,
  ...
}
```

Para resolver este requerimiento asumí que solo el tipo de `Material` se debe
corresponde con un `Tipo` de `Prenda`. Si se quisieran incluir todos los 
atributos relacionados al material de una `Prenda` mencionados (`Color`es, 
`Material` y `Trama`) a la validación según el `Tipo`, consideraría crear una 
clase que los contenga y que el `Tipo` contenga todas las restricciones 
posibles. 

Me refiero a "restricciones" y no "valores" ya que ese universo es muy 
amplio (siendo el producto de los valores posibles de `Color`(^2) x `Material` x 
`Trama`), por lo que la carga de cada uno de los mismos resultaría ser una 
tarea muy tediosa.

### Requerimiento 5

> Como usuario/a de QuéMePongo, quiero poder no indicar ninguna trama para una
  material, y que por defecto ésta sea lisa.

El método `crearPrenda` de un `Borrador` va a incluir ese valor por defecto en
caso de que sea `null`:

```ts
class Borrador {
    crearPrenda() {
        return new Prenda(
            // ...
            this.trama != null ? this.trama : Trama.LISA, 
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
    if (colorPrimario == null) {
      throw new PrendaInvalidaException(...)
    }
    
    return new Prenda(
        // ...
        this.colorPrimario,
        // ...
    )
  }
}
```

Por lo tanto, teniendo en cuenta los 3 requerimientos anteriores, se considera 
peligroso hacer `new` de una`Prenda` en cualquier parte del código que no sea el
método `crearPrenda` de `Borrador`, ya que esa instancia no va a contar con las 
validaciones que se incluyen en el mismo.

### Bonus 1

> Como usuario/a de QueMePongo, quiero poder recibir sugerencias de uniformes
armados.

Como estos uniformes prearmados no van a variar demasiado en el tiempo, vamos a
crearlos directamente en el código utilizando una implementación de la clase
`UniformeFactory`:

```ts
abstract class UniformeFactory {
    crearUniforme() {
        return new Uniforme(
            this.getBorradorSuperior().crearPrenda(),
            this.getBorradorInferior().crearPrenda(),
            this.getBorradorCalzado().crearPrenda()    
        )
    }
}
```

### Bonus 2

> Como usuario/a de QueMePongo, quiero que un uniforme siempre conste de una
  prenda superior, una inferior y un calzado

Se crea una clase `Uniforme` que conste de tres `Prenda`s:

```ts
class Uniforme {
    superior: Prenda
    inferior: Prenda
    calzado: Prenda
}
```

Pensé en agregar validaciones para asegurarme de que cada parte sea superior,
inferior y calzado respectivamente, pero como van a ser cargadas desde el
código elijo confiar en que voy a obtener solo valores correctos.

### Bonus 3

> Como admin de QueMePongo, quiero poder configurar diferentes uniformes para
  distintas instituciones (Ej: para el colegio San Juan debe ser una chomba
  verde de piqué, un pantalón de acetato gris y zapatillas blancas, mientras que
  para el Instituto Johnson siempre será una camisa blanca, pantalón de vestir
  negro y zapatos negros)

Se van a cargar los uniformes extendiendo la clase `UniformeFactory`, 
devolviendo el `Borrador` correcto para crear la `Prenda`:

```ts
class UniformeSanJuanFactory extends UniformeFactory {
    Borrador borradorSuperior = new Borrador(Tipo.CHOMBA)
          .conColorPrimario(Color.GREEN)
          .conMaterial(Material.PIQUE)
           // ... etc
  
    // ... getters
}
```

Por un lado es más simple al no requerir de una interfaz de usuario ni una 
base de datos para cargarlos, pero por otro no es tan extensible ya que si se 
agregan muchos colegios van a haber muchas clases en el código. 

<!--
## Cambios post Puesta en Común

### Diagrama de Clases

![diagrama](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/RaniAgus/dds-jv-2022-que-me-pongo/main/docs/diagramas/iteracion-2-cambios.puml)

### Requerimiento 1

### Requerimiento 2

...

### Requerimiento N
-->