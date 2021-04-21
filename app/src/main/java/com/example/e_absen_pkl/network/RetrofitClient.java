package com.example.e_absen_pkl.network;
import com.facebook.stetho.okhttp3.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private static  final String BASE_URL = "http://192.168.20.5:8000/api/";

    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = builRetrofit(client);

    private static OkHttpClient buildClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept","application/json")
                                .addHeader("Connection","close");

                        request = builder.build();
                        return chain.proceed(request);
                    }
                });
        if (BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }
    private static Retrofit builRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service){
        return retrofit.create(service);
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }
}
