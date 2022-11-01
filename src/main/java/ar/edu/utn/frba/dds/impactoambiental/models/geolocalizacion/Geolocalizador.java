package ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.HttpRequestException;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Geolocalizador {
  private final String apiKey;

  public Geolocalizador(String apiKey) {
    this.apiKey = apiKey;
  }

  public List<Pais> getPaises() {
    return consultar(GeoddsApi.INSTANCE.getPaises(1, apiKey));
  }

  public Optional<Pais> findPais(String nombre) {
    return getPaises().stream()
        .filter(pais -> pais.getNombre().equals(nombre))
        .findFirst();
  }

  public List<Provincia> getProvincias(Pais pais) {
    return consultar(GeoddsApi.INSTANCE.getProvincias(1, pais.getId(), apiKey));
  }

  public Optional<Provincia> findProvincia(Pais pais, String nombre) {
    return getProvincias(pais).stream()
        .filter(provincia -> provincia.getNombre().equals(nombre))
        .findFirst();
  }

  public List<Municipio> getMunicipios(Provincia provincia) {
    return paginar(offset -> GeoddsApi.INSTANCE.getMunicipios(offset, provincia.getId(), apiKey));
  }

  public Optional<Municipio> findMunicipio(Provincia provincia, String nombre) {
    return getMunicipios(provincia).stream()
        .filter(municipio -> municipio.getNombre().equals(nombre))
        .findFirst();
  }

  public List<Localidad> getLocalidades(Municipio municipio) {
    return paginar(offset -> GeoddsApi.INSTANCE.getLocalidades(offset, municipio.getId(), apiKey));
  }

  public Optional<Localidad> findLocalidad(Municipio municipio, String nombre) {
    return getLocalidades(municipio).stream()
        .filter(localidad -> localidad.getNombre().equals(nombre))
        .findFirst();
  }

  public Optional<Ubicacion> getUbicacion(String pais, 
                                          String provincia, 
                                          String municipio, 
                                          String localidad,
                                          String calle,
                                          String altura) {
      return findPais(pais)
        .flatMap(p -> findProvincia(p, provincia))
        .flatMap(pr -> findMunicipio(pr, municipio))
        .flatMap(mu -> findLocalidad(mu, localidad))
        .map(lo -> new Ubicacion(lo.getId(), calle, altura));
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
