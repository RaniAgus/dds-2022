package ar.edu.utn.frba.dds.quemepongo.dependencies.clima;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
  static OpenWeatherApi create() {
    return new Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenWeatherApi.class);
  }

  @GET("weather")
  Call<CurrentWeather> getCurrentWeather(@Query("q") String city,
                                         @Query("units") String units,
                                         @Query("appid") String key);
}
