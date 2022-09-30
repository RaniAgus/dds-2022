# Séptima iteración

## Requerimiento 1

> Como usuario de QueMePongo quiero ver todas las prendas que tengo en mi
  guardarropa desde el navegador para poder administrarlas

- Ruta: `/guardarropas/:id/prendas`
- Método: `GET`
- Códigos de respuesta:
  - 200: OK
  - 404: Guardarropa no encontrado
  - 500: Error interno
- Body de respuesta:
  - 200: Lista de prendas en el guardarropa
  - 404: Mensaje de error
  - 500: Mensaje de error

## Requerimiento 2

> Como usuario de QueMePongo, quiero crear una prenda desde el navegador

- Ruta: `/guardarropas/:id/prendas`
- Método: `POST`
- Body:
  - Tipo de prenda
  - Color primario
  - Color secundario (opcional)
  - Material
  - Trama
- Códigos de respuesta:
  - 201: Prenda creada
  - 400: Prenda inválida
  - 404: Guardarropa no encontrado
  - 500: Error interno

## Requerimiento 3

> Como usuario de QueMePongo quiero ver una prenda en particular que tengo en mi
  guardarropa para poder editarla

- Ruta: `/guardarropas/:id/prendas/:id`
- Método: `GET`
- Códigos de respuesta:
  - 200: OK
  - 404: Prenda no encontrada
  - 500: Error interno
- Body de respuesta:
  - 200: Prenda
  - 404: Mensaje de error
  - 500: Mensaje de error

## Requerimiento 4

> Como usuario de QueMePongo, quiero poder eliminar una prenda de mi guardarropa

- Ruta: `/guardarropas/:id/prendas/:id`
- Método: `DELETE`
- Códigos de respuesta:
  - 200: Prenda eliminada
  - 404: Prenda no encontrada
  - 500: Error interno
- Body de respuesta:
  - 200: Mensaje de éxito
  - 404: Mensaje de error
  - 500: Mensaje de error

## Requerimiento 5

> Como usuario de QueMePongo, quiero poder ver mis eventos para administrarlos

- Ruta: `/eventos`
- Método: `GET`
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
- Códigos de respuesta:
  - 200: OK
  - 404: Evento no encontrado
  - 500: Error interno
- Body de respuesta:
  - 200: Lista de sugerencias
  - 404: Mensaje de error
  - 500: Mensaje de error
