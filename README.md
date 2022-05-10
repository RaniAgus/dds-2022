# Monedero

## Contexto

Este repositorio contiene el código de un _monedero virtual_, al que podemos agregarle y quitarle dinero, a través 
de los métodos `Monedero.sacar` y `Monedero.poner`, respectivamente. 
Pero hay algunos problemas: por un lado el código no está muy bien testeado, y por el otro, hay numeros _code smells_. 

## Consigna

Tenés seis tareas: 

 1. :fork_and_knife: Hacé un repositorio usando este template (presionando desde Github el botón _use this template_)
 2. :arrow_down: Descargalo y construí el proyecto, utilizando `maven`
 2. :nose: Identificá y anotá todos los _code smells_ que encuentres 
 3. :test_tube: Agregá los tests faltantes y mejorá los existentes. 
     * :eyes: Ojo: ¡un test sin ningún tipo de aserción está incompleto!
 4. :rescue_worker_helmet: Corregí smells, de a un commit por vez. 
 5. :arrow_up: Subí todos los cambios a tu _fork_
 6. :bug: Activá los issues de Github desde https://github.com/TU_GITHUB_USERNAME/dds-monedero-java8/settings así podemos darte nuestras devoluciones

## Code Smells 

### Misplaced Method

La `Cuenta` no debería delegar la actualización de su saldo ni de sus
movimientos:

```java
class Cuenta {
  public void poner(double cuanto) {
    // ...
    new Movimiento(LocalDate.now(), cuanto, true).agregateA(this);
  }

  public void sacar(double cuanto) {
    // ...
    new Movimiento(LocalDate.now(), cuanto, false).agregateA(this);
  }
}

class Movimiento {
  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto, esDeposito);
  }

  public double calcularValor(Cuenta cuenta) {
    if (esDeposito) {
      return cuenta.getSaldo() + getMonto();
    } else {
      return cuenta.getSaldo() - getMonto();
    }
  }
}
```

### Long Method

Los métodos `poner` y `sacar` tienen muchas responsabilidades:
1. Validar que el monto del `Movimiento` sea positivo
2. Validar que el `Movimiento` corresponda con el saldo y los movimientos del día
3. Crear un `Movimiento` con fecha de hoy
4. Agregar el movimiento a la `Cuenta`

```java
  public void poner(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }

    if (getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }

    new Movimiento(LocalDate.now(), cuanto, true).agregateA(this);
  }

  public void sacar(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
    new Movimiento(LocalDate.now(), cuanto, false).agregateA(this);
  }
  
  
```

Delego la responsabilidad de validar el monto al `Movimiento` y separo el 
`agregarMovimiento()` en dos métodos `agregarDeposito()` y `agregarExtraccion()`
para que las validaciones queden allí.

Nota: creo que `poner` y `sacar` son fachadas que a futuro se pueden reemplazar
por los métodos `agregarMovimiento()` y `agregarDeposito()`, pero al tratarse de
un refactor voy a mantenerlos así como están para evitar romper funcionalidad y
tests.

### Primitive Obsession y Type Test

Un `Movimiento` podría usar polimorfismo para determinar si `fueDepositado()` o
`fueExtraido()`, en lugar de un `boolean`:

```java
class Movimiento { 
  private boolean esDeposito;

  // ...
  
  public boolean isDeposito() {
    return esDeposito;
  }

  public boolean isExtraccion() {
    return !esDeposito;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }
}
```

### Data Clump

Los parámetros de `agregarMovimiento()` no solo están fuertemente acoplados a un
`Movimiento`:

```java
  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }
```

Sino que aparte quien lo invoca es un `Movimiento` ya existente, por lo que se
estarían creando dos instancias cuando podría ser una:

```java
class Movimiento {
  // ...
  public void agregateA(Cuenta cuenta) {
    // ...
    cuenta.agregarMovimiento(fecha, monto, esDeposito);
  }
}
```

### Temporary Field

El valor del `saldo` puede ser calculable en base a los movimientos:

```java
public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();

  public double getSaldo() {
    // movimientos.sum(movimiento -> movimiento.getValor())
    return saldo;
  }
}
```


## Tecnologías usadas

* Java 8.
* JUnit 5. :warning: La versión 5 de JUnit es la más nueva del framework y presenta algunas diferencias respecto a la versión "clásica" (JUnit 4). Para mayores detalles, ver:
    *  [Apunte de herramientas](https://docs.google.com/document/d/1VYBey56M0UU6C0689hAClAvF9ILE6E7nKIuOqrRJnWQ/edit#heading=h.dnwhvummp994)
    *  [Entrada de Blog (en inglés)](https://www.baeldung.com/junit-5-migration)
    *  [Entrada de Blog (en español)](https://www.paradigmadigital.com/dev/nos-espera-junit-5/)
* Maven 3.3 o superior
 
