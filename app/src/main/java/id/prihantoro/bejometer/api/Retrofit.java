package id.prihantoro.bejometer.api;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wahyu Prihantoro on 21-Aug-16.
 */
@EBean
public class Retrofit {
    String baseUrl = "http://alfan.coderhutan.com/bejometer/jonwahyu/";

    @RootContext
    Context context;

    retrofit2.Retrofit retrofit;

    public <T> T getService(Class<T> serviceClass) {
        return getRetrofit().create(serviceClass);
    }

    public synchronized retrofit2.Retrofit getRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("User-Agent", "Android: " + Build.MANUFACTURER + " " + Build.MODEL)
                        .method(original.method(), original.body())
                        .build();

                Log.d("wahyu debug", request.toString());
                Log.d("wahyu debug", "Android: " + Build.MANUFACTURER + " " + Build.MODEL);
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        retrofit = new retrofit2.Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
