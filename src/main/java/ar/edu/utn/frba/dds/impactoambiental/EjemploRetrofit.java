package ar.edu.utn.frba.dds.impactoambiental;

import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.*;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.Contacto;
import ar.edu.utn.frba.dds.impactoambiental.models.notificaciones.NotificadorPorWhatsApp;

import java.util.List;

public class EjemploRetrofit {
  private static final String apiKey = "Bearer " + System.getenv("GEODDS_API_KEY");

  public static void main(String[] args) {
    probarGeolocalizador();
    probarWhatsApp();
    System.exit(0);
  }

  private static void probarGeolocalizador() {
    Geolocalizador geolocalizador = new Geolocalizador(apiKey);

    List<Pais> paises = geolocalizador.getPaises();
    paises.forEach(System.out::println);

    List<Provincia> provincias = geolocalizador.getProvincias(paises.get(0));
    provincias.forEach(System.out::println);

    List<Municipio> municipios = geolocalizador.getMunicipios(provincias.get(0));
    municipios.forEach(System.out::println);

    List<Localidad> localidades = geolocalizador.getLocalidades(municipios.get(116));
    localidades.forEach(System.out::println);

    Distancia distancia = geolocalizador.medirDistancia(
        new Ubicacion(1, "maipu", "100"),
        new Ubicacion(localidades.get(1).getId(), "O'Higgins", "200")
    );
    System.out.println(distancia);
  }

  private static void probarWhatsApp() {
    Contacto contacto = new Contacto("", "<completar>");
    NotificadorPorWhatsApp.INSTANCE.enviarGuiaRecomendacion(contacto, System.getenv("RECOMENDACIONES_URL"));
  }

}
