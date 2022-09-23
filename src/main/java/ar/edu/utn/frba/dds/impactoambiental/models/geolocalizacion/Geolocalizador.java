package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.HttpRequestException;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Geolocalizador {
  private final String apiKey;

  public Geolocalizador(String apiKey) {
    this.apiKey = apiKey;
  }

  public List<Pais> getPaises() {
    return consultar(GeoddsApi.INSTANCE.getPaises(1, apiKey));
  }

  public List<Provincia> getProvincias(Pais pais) {
    return consultar(GeoddsApi.INSTANCE.getProvincias(1, pais.getId(), apiKey));
  }

  public List<Municipio> getMunicipios(Provincia provincia) {
    return paginar(offset -> GeoddsApi.INSTANCE.getMunicipios(offset, provincia.getId(), apiKey));
  }

  public List<Localidad> getLocalidades(Municipio municipio) {
    return paginar(offset -> GeoddsApi.INSTANCE.getLocalidades(offset, municipio.getId(), apiKey));
  }

  public Distancia medirDistancia(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino) {
    return consultar(GeoddsApi.INSTANCE.getDistancia(
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
    for (long offset = 1; ; offset++) {
      List<T> res = consultar(call.apply(offset));
      if (res.isEmpty()) {
        break;
      }
      total.addAll(res);
    }
    return total;
  }

  private static <T> T consultar(Call<T> call) {
    try {
      Response<T> response = call.execute();
      if (response.body() == null) {
        throw new HttpRequestException(new Gson()
            .fromJson(response.errorBody().charStream(), GeoddsError.class)
            .getErrorMessage());
      }
      return response.body();
    } catch (IOException e) {
      throw new HttpRequestException("Ocurrió un error al interactuar con GeoDDS API", e);
    }
  }
}
