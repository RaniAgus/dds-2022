package models.geolocalizacion;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import java.util.List;

public interface GeoddsApi {
  GeoddsApi INSTANCE = new Retrofit.Builder()
      .baseUrl("https://ddstpa.com.ar/api/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(GeoddsApi.class);

  @GET("/api/paises")
  Call<List<Pais>> getPaises(@Query("offset") long offset,
                             @Header("Authorization") String authorization);

  @GET("/api/provincias")
  Call<List<Provincia>> getProvincias(@Query("offset") long offset,
                                      @Query("paisId") Integer paisId,
                                      @Header("Authorization") String authorization);

  @GET("/api/municipios")
  Call<List<Municipio>> getMunicipios(@Query("offset") long offset,
                                      @Query("provinciaId") Integer provinciaId,
                                      @Header("Authorization") String authorization);

  @GET("/api/localidades")
  Call<List<Localidad>> getLocalidades(@Query("offset") long offset,
                                       @Query("municipioId") Integer municipioId,
                                       @Header("Authorization") String authorization);

  @GET("/api/distancia")
  Call<Distancia> getDistancia(@Query("localidadOrigenId") Integer localidadOrigenId,
                               @Query("calleOrigen") String calleOrigen,
                               @Query("alturaOrigen") String alturaOrigen,
                               @Query("localidadDestinoId") Integer localidadDestino,
                               @Query("calleDestino") String calleDestino,
                               @Query("alturaDestino") String alturaDestino,
                               @Header("Authorization") String authorization);
}
