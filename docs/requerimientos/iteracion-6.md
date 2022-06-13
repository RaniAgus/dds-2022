# Sexta Iteración

En esta iteración introduciremos el concepto de "alertas meteorológicas". Estas
alertas son avisos que nos llegan de parte de los proveedores de clima para
informarnos de eventos tales como "Granizo" o "Tormenta". Trabajaremos sobre el
siguiente requerimiento:

> Como usuario/a de QueMePongo quiero poder enterarme si se emitió alguna alerta
> meteorológica para poder actuar en consecuencia

Inicialmente, comenzaremos atacando solamente los siguientes requerimientos
específicos:

- Como usuario/a de QueMePongo quiero tener una sugerencia diaria de qué ponerme
  y que todas las mañanas, diariamente, esta sea actualizada
- Como empleado/a de QueMePongo quiero poder disparar el cálculo de sugerencias
  diarias para todos los usuarios para poder ejecutar esta acción a principio de
  cada día.
- Como usuario/a de QueMePongo, quiero poder conocer cuáles son las últimas
  alertas meteorológicas publicadas en el sistema para estar informado (pudiendo
  verlas, por ejemplo, al entrar en quemepongo.com)
- Como empleado/a de QueMePongo, necesito poder disparar un proceso que consulte
  y actualice la lista de alertas publicadas en el sistema para tener control
  sobre cuándo se publican las mismas
- Como usuario/a de QuéMePongo quiero que se actualice mi sugerencia diaria con
  las condiciones climáticas actualizadas cuando se genere algún alerta durante
  el día
- Como usuario/a de QueMePongo quiero tener la posibilidad de que ante una
  alerta de tormenta la app me notifique que debo llevarme también un paraguas
- Como usuario/a de QueMePongo quiero que ante una alerta meteorológica de
  granizo la app me notifique que evite salir en auto
- Como usuario/a de QueMePongo quiero poder recibir un mail avisándome si se
  generó algún alerta meteorológico y cuál
- Como usuario/a de QuéMePongo quiero poder configurar cuáles de estas acciones
  (notificaciones, mail, recálculo) quiero que se ejecuten y cuáles no, además
  de soportar nuevas acciones a futuro. (No nos interesará, sin embargo,
  soportar nuevas alertas)

Se cuenta con una nueva operación del SDK de AccuWeather a la que se le puede
consultar alertas meteorológicas vigentes

```java
interface AccuWeatherAPI {
  List<Map<String, Object>> getWeather(String city)
  Map<String, List<String>> getAlerts(String city)
}
```

En su documentación, incluyen el siguiente ejemplo de uso:

```java
AccuWeatherAPI apiClima = new AccuWeatherAPI();
Map<String, Object> alertas = apiClima.getAlertas("Buenos Aires");
alertas.get("CurrentAlerts"); //Devuelve un objeto como ["STORM", "HAIL", ...]
```

Se cuenta con una API Para Java del sistema operativo que envía notificaciones
en la pantalla al usuario mientras navega el sitio.

```java
interface NotificationService {
  notify(text)
}
```

Se cuenta con un servicio externo de mails

```java
interface MailSender {
  send(address,message)
}
```

## Bonus

- Como administrador/a de QueMePongo, quiero que las sugerencias diarias se
  calculen automáticamente sin que un empleado necesite disparar esta acción
  manualmente
- Como administrador/a de QueMePongo, quiero que las alertas se publiquen en el
  sitio automáticamente sin que un empleado necesite disparar esta acción
  manualmente
