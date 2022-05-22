package models.miembro;

import models.Ubicacion;
import models.api.*;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Geolocalizador {
  private final String apiKey = "Bearer " + System.getenv("GEODDS_API_KEY");

  GeoddsApi api = GeoddsApi.INSTANCE;

  public List<Pais> getPaises(){
    return consultar(api.getPaises(1, apiKey));
  }

  public List<Provincia> getProvincias(Pais pais){
    return consultar(api.getProvincias(1, pais.getId(), apiKey));
  }

  public List<Municipio> getMunicipios(Provincia provincia){
    return paginar(offset -> api.getMunicipios(offset, provincia.getId(), apiKey));
  }

  public List<Localidad> getLocalidades(Municipio municipio){
    return paginar(offset -> api.getLocalidades(offset, municipio.getId(), apiKey));
  }

  public Distancia medirDistancia(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino){
    return consultar(api.getDistancia(
        ubicacionOrigen.getIdLocalidad(),
        ubicacionOrigen.getCalle(),
        ubicacionOrigen.getAltura(),
        ubicacionDestino.getIdLocalidad(),
        ubicacionDestino.getCalle(),
        ubicacionDestino.getAltura(),
        apiKey
    ));
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
