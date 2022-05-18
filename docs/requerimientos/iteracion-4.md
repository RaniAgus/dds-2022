# Cuarta Iteración

En esta iteración continuaremos trabajando sobre las sugerencias de atuendos:

> Como usuarie de QuéMePongo, quiero recibir sugerencias de atuendos para
> vestirme ajustándome a las condiciones climáticas con ropa de mi agrado

En esta oportunidad, atacaremos solamente los siguientes requerimientos
específicos:

- Como usuarie de QuéMePongo, quiero poder conocer las condiciones climáticas de
  Buenos Aires en un momento dado para obtener sugerencias acordes.

- Como usuarie de QuéMePongo, quiero poder recibir sugerencias de atuendos que
  tengan una prenda para cada categoría, aunque a futuro podrán tener más (Ej.:
  Una remera, un pantalón, zapatos y un gorro).

- Como usuarie de QuéMePongo, quiero que al generar una sugerencia las prendas
  sean acordes a la temperatura actual sabiendo que para cada prenda habrá una
  temperatura hasta la cual es adecuada. (Ej.: "Remera de mangas largas" no es
  apta a más de 20°C)

- Como administradore de QuéMePongo, quiero poder configurar fácilmente
  diferentes servicios de obtención del clima para ajustarme a las cambiantes
  condiciones económicas.

- Como stakeholder[^1] de QuéMePongo, quiero poder asegurar la calidad de mi
  aplicación sin incurrir en costos innecesarios.

Además, tras investigar en el mercado encontramos que la conocida empresa
AccuWeather provee un SDK[^2] para Java que nos entrega una lista con el clima
de las próximas 12 horas en un diccionario:

![image](https://user-images.githubusercontent.com/39303639/168929983-fd590a9b-f00d-413e-9587-469174f48c5b.png)

En su documentación, incluyen el siguiente ejemplo de uso[^3]:

```java
AccuWeatherAPI apiClima = new AccuWeatherAPI();
List<Map<String, Object>> condicionesClimaticas = apiClima.getWeather("Buenos Aires, Argentina");
condicionesClimaticas.get(0).get("PrecipitationProbability"); // Devuelve un número del 0 al 1
```

Y nos cobra 0,05 USD por cada vez que la llamamos a partir del décimo llamado
diario.

[^1]:
    "Stakeholder" se refiere a los principales _interesados_ en el producto,
    quienes disparan su creación. Estos pueden ser inversores, directores de
    área, responsables del negocio, etc.

[^2]:
    Conjunto de componentes proveídos a modo de biblioteca para una determinada
    tecnología.

[^3]:
    Para utilizar la API, descargar e incluir el siguiente archivo en su código:
    [AccuWeatherAPI.java](https://github.com/dds-utn/api-accuweather-objetos/blob/master/src/main/java/AccuWeatherAPI.java)
