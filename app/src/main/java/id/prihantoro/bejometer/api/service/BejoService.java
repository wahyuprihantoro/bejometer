package id.prihantoro.bejometer.api.service;

import java.util.Map;

import id.prihantoro.bejometer.api.respone.BejoResponse;
import id.prihantoro.bejometer.api.respone.KonsultasiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Wahyu Prihantoro on 21-Aug-16.
 */
public interface BejoService {
    @GET("ngitung/{hashcode}")
    Call<BejoResponse> njaluk(@Path("hashcode") String hash, @QueryMap Map<String, String> options);
    @GET("konsultasi/{hashcode}")
    Call<KonsultasiResponse> konsultasi(@Path("hashcode") String hash, @Query("nama") String nama);
}
