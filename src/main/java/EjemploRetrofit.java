import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.*;
import models.geolocalizacion.Geolocalizador;
import java.util.List;

public class EjemploRetrofit {
  private static final String apiKey = "Bearer " + System.getenv("GEODDS_API_KEY");

  public static void main(String[] args) {
    Geolocalizador geolocalizador = new Geolocalizador(apiKey);

    List<Pais> paises = geolocalizador.getPaises();
    List<Provincia> provincias = geolocalizador.getProvincias(paises.get(0));
    List<Municipio> municipios = geolocalizador.getMunicipios(provincias.get(0));
    List<Localidad> localidades = geolocalizador.getLocalidades(municipios.get(116));
    Distancia distancia = geolocalizador.medirDistancia(
        new Ubicacion(1, "maipu", "100"),
        new Ubicacion(localidades.get(1).getId(), "O'Higgins", "200")
    );

    provincias.forEach(System.out::println);
    municipios.forEach(System.out::println);
    localidades.forEach(System.out::println);
    System.out.println(distancia);
  }

}
