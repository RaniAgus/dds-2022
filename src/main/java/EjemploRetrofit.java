import java.util.List;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;
import models.geolocalizacion.Localidad;
import models.geolocalizacion.Municipio;
import models.geolocalizacion.Pais;
import models.geolocalizacion.Provincia;
import models.geolocalizacion.Ubicacion;

public class EjemploRetrofit {
  private static final String apiKey = "Bearer " + System.getenv("GEODDS_API_KEY");

  public static void main(String[] args) {
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

    System.exit(0);
  }

}
