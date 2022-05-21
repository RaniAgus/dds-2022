import models.api.*;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EjemploRetrofit {
  private static final String key = "Bearer " + System.getenv("DISTANCIA_API_KEY");

  public static void main(String[] args) {
    DistanciaAPI api = DistanciaAPI.INSTANCE;

    List<Pais> paises = consultar(api.getPaises(1, key));
    List<Provincia> provincias = consultar(api.getProvincias(1, paises.get(0).getId(), key));
    List<Municipio> municipios = paginar(offset -> api.getMunicipios(offset, provincias.get(0).getId(), key));
    List<Localidad> localidades = paginar(offset -> api.getLocalidades(offset, municipios.get(116).getId(), key));
    Distancia distancia = consultar(api.getDistancia(
        1, // Caa Yari, Leandro N. Alem, Misiones
        "maipu",
        "100",
        localidades.get(1).getId(),
        "O'Higgins",
        "200",
        key
    ));

    provincias.forEach(System.out::println);
    municipios.forEach(System.out::println);
    localidades.forEach(System.out::println);
    System.out.println(distancia);
  }

  private static <T> List<T> paginar(Function<Long, Call<List<T>>> call) {
    List<T> total = new ArrayList<>();
    for (long offset = 1;; offset++) {
      List<T> res = consultar(call.apply(offset));
      if (res.isEmpty()) break;
      total.addAll(res);
    }
    return total;
  }

  private static <T> T consultar(Call<T> call) {
    try {
      System.out.println("Request{method=GET, url=" + call.request().url() + "}");
      Response<T> response = call.execute();
      return Optional.ofNullable(response.body()).orElseThrow(RuntimeException::new);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
