# Séptima iteración

## Requerimiento 1

> Como usuario de QueMePongo quiero ver todas las prendas que tengo en mi
  guardarropa desde el navegador para poder administrarlas

- Ruta: `/guardarropas/:id/prendas`
- Método: `GET`
- Body: `N/A`
- Códigos de respuesta:
  - 200: OK
  - 404: Guardarropa no encontrado
  - 500: Error interno
- Body de respuesta:
  - 200: Lista de prendas del guardarropa
```json
[
  {
    "id": 1,
    "tipo": "CAMISA",
    "categoria": "PARTE_SUPERIOR",
    "material": "ALGODON",
    "colorPrimario": "BLANCO",
    "colorSecundario": "AZUL",
    "trama": "RAYADA",
    "temperaturaMinima": 10,
    "temperaturaMaxima": 20
  },
  {
    "id": 2,
    "tipo": "CAMISA",
    "categoria": "PARTE_SUPERIOR",
    "material": "ALGODON",
    "colorPrimario": "BLANCO",
    "colorSecundario": "AZUL",
    "trama": "RAYADA",
    "temperaturaMinima": 10,
    "temperaturaMaxima": 20
  }
]
```
  - 404: Mensaje de error
  - 500: Mensaje de error

## Requerimiento 2

> Como usuario de QueMePongo, quiero crear una prenda desde el navegador

- Ruta: `/guardarropas/:id/prendas`
- Método: `POST`
- Body: Prenda a crear
```json
{
  "tipo": "CAMISA",
  "categoria": "PARTE_SUPERIOR",
  "material": "ALGODON",
  "colorPrimario": "BLANCO",
  "colorSecundario": "AZUL",
  "trama": "RAYADA",
  "temperaturaMinima": 10,
  "temperaturaMaxima": 20
}
```
- Códigos de respuesta:
  - 201: Prenda creada
  - 400: Prenda inválida
  - 404: Guardarropa no encontrado
  - 500: Error interno
- Body de respuesta:
  - 201: Prenda creada
```json
{
  "id": 1,
  "tipo": "PANTALON",
  "categoria": "PARTE_SUPERIOR",
  "material": "ALGODON",
  "colorPrimario": "AZUL",
  "colorSecundario": "ROJO",
  "trama": "RAYADA",
  "temperaturaMinima": 10,
  "temperaturaMaxima": 20
}
```
  - 400: Mensaje de error
  - 404: Mensaje de error
  - 500: Mensaje de error

## Requerimiento 3

> Como usuario de QueMePongo quiero ver una prenda en particular que tengo en mi
  guardarropa...

- Ruta: `/guardarropas/:id/prendas/:id`
- Método: `GET`
- Body: `N/A`
- Códigos de respuesta:
  - 200: OK
  - 404: Prenda no encontrada
  - 500: Error interno
- Body de respuesta:
  - 200: Prenda
```json
{
  "id": 1,
  "tipo": "CAMISA",
  "categoria": "PARTE_SUPERIOR",
  "material": "ALGODON",
  "colorPrimario": "BLANCO",
  "colorSecundario": "AZUL",
  "trama": "RAYADA",
  "temperaturaMinima": 10,
  "temperaturaMaxima": 20
}
```
  - 404: Mensaje de error
  - 500: Mensaje de error

> ...para poder editarla

- Ruta: `/guardarropas/:id/prendas/:id`
- Método: `PUT`
- Body: Prenda a editar
```json
{
  "tipo": "REMERA",
  "categoria": "PARTE_SUPERIOR",
  "material": "LINO",
  "colorPrimario": "BLANCO",
  "colorSecundario": "AZUL",
  "trama": "RAYADA",
  "temperaturaMinima": 10,
  "temperaturaMaxima": 20
}
```
- Códigos de respuesta:
  - 200: Prenda editada
  - 400: Prenda inválida
  - 404: Prenda no encontrada
  - 500: Error interno
- Body de respuesta:
  - 200: Prenda editada
```json
{
  "id": 1,
  "tipo": "REMERA",
  "categoria": "PARTE_SUPERIOR",
  "material": "LINO",
  "colorPrimario": "BLANCO",
  "colorSecundario": "AZUL",
  "trama": "RAYADA",
  "temperaturaMinima": 10,
  "temperaturaMaxima": 20
}
```
  - 400: Mensaje de error
  - 404: Mensaje de error
  - 500: Mensaje de error

## Requerimiento 4

> Como usuario de QueMePongo, quiero poder eliminar una prenda de mi guardarropa

- Ruta: `/guardarropas/:id/prendas/:id`
- Método: `DELETE`
- Body: `N/A`
- Códigos de respuesta:
  - 204: Prenda eliminada
  - 404: Prenda no encontrada
  - 500: Error interno
- Body de respuesta:
  - 204: `N/A`
  - 404: Mensaje de error
  - 500: Mensaje de error

## Requerimiento 5

> Como usuario de QueMePongo, quiero poder ver mis eventos para administrarlos

- Ruta: `/eventos`
- Método: `GET`
- Body: `N/A`
- Códigos de respuesta:
  - 200: OK
  - 500: Error interno
- Body de respuesta:
  - 200: Lista de eventos
  - 500: Mensaje de error

## Requerimiento 6

> Como usuario de QueMePongo, quiero poder abrir las sugerencias de prendas para
  un evento en mi navegador

- Ruta: `/eventos/:id/sugerencias`
- Método: `GET`
- Body: `N/A`
- Códigos de respuesta:
  - 200: OK
  - 404: Evento no encontrado
  - 500: Error interno
- Body de respuesta:
  - 200: Lista de sugerencias
```json
[
    {
        "id": 1,
        "prendaSuperior": {
            "id": 1,
            "tipo": "CAMISA",
            "categoria": "PARTE_SUPERIOR",
            "material": "ALGODON",
            "colorPrimario": "BLANCO",
            "colorSecundario": "AZUL",
            "trama": "RAYADA",
            "temperaturaMinima": 10,
            "temperaturaMaxima": 20
        },
        "prendaInferior": {
            "id": 2,
            "tipo": "PANTALON",
            "categoria": "PARTE_INFERIOR",
            "material": "ALGODON",
            "colorPrimario": "AZUL",
            "colorSecundario": "ROJO",
            "trama": "RAYADA",
            "temperaturaMinima": 10,
            "temperaturaMaxima": 20
        },
        "calzado": {
            "id": 3,
            "tipo": "ZAPATILLAS",
            "categoria": "CALZADO",
            "material": "CUERO",
            "colorPrimario": "NEGRO",
            "trama": "LISA",
            "temperaturaMinima": 10,
            "temperaturaMaxima": 20
        }
    }
]
```
  - 404: Mensaje de error
  - 500: Mensaje de error
