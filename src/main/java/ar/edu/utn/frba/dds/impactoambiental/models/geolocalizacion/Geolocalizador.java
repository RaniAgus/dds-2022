package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.RespuestaNoObtenidaException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class Geolocalizador {
  private String apiKey;

  public Geolocalizador(String apiKey) {
    this.apiKey = apiKey;
  }

  public List<Pais> getPaises() {
    return consultar(
        GeoddsApi.INSTANCE.getPaises(1, apiKey),
        "No se pudo obtener la lista de países"
    );
  }

  public List<Provincia> getProvincias(Pais pais) {
    return consultar(
        GeoddsApi.INSTANCE.getProvincias(1, pais.getId(), apiKey),
        "No se pudo obtener la lista de provincias para " + pais.getNombre()
    );
  }

  public List<Municipio> getMunicipios(Provincia provincia) {
    return paginar(
        offset -> GeoddsApi.INSTANCE.getMunicipios(offset, provincia.getId(), apiKey),
        "No se pudo obtener la lista de municipios para " + provincia.getNombre()
    );
  }

  public List<Localidad> getLocalidades(Municipio municipio) {
    return paginar(
        offset -> GeoddsApi.INSTANCE.getLocalidades(offset, municipio.getId(), apiKey),
        "No se pudo obtener la lista de localidades para " + municipio.getNombre()
    );
  }

  public Distancia medirDistancia(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino) {
    return consultar(
        GeoddsApi.INSTANCE.getDistancia(
            ubicacionOrigen.getIdLocalidad(),
            ubicacionOrigen.getCalle(),
            ubicacionOrigen.getAltura(),
            ubicacionDestino.getIdLocalidad(),
            ubicacionDestino.getCalle(),
            ubicacionDestino.getAltura(),
            apiKey
        ),
        "No se pudo medir la distancia entre: " + ubicacionOrigen
            + " y " + ubicacionDestino
    );
  }

  private static <T> List<T> paginar(Function<Long, Call<List<T>>> call, String message) {
    List<T> total = new ArrayList<>();
    for (long offset = 1;; offset++) {
      List<T> res = consultar(call.apply(offset), message);
      if (res.isEmpty()) {
        break;
      }
      total.addAll(res);
    }
    if (total.isEmpty()) {
      throw new RespuestaNoObtenidaException(message);
    }
    return total;
  }

  private static <T> T consultar(Call<T> call, String message) {
    try {
      Response<T> response = call.execute();
      return Optional.ofNullable(response.body())
          .orElseThrow(() -> new RespuestaNoObtenidaException(message));
    } catch (IOException e) {
      throw new RespuestaNoObtenidaException(message, e);
    }
  }
}
