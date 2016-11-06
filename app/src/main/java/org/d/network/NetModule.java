package org.d.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private static final int TIMEOUT = 60; //seconds
    private String mBaseUrlTlycs;

    public NetModule(String baseUrlTlycs) {
        mBaseUrlTlycs = baseUrlTlycs;
    }

    @NonNull
    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }

    @Provides
    @Singleton
    public TheLifeYouCanSaveService provideTheLifeYouCanSaveService(Retrofit.Builder retrofitBuilder) {
        Retrofit retrofit = retrofitBuilder.baseUrl(mBaseUrlTlycs).build();
        TheLifeYouCanSaveService theLifeYouCanSaveService = retrofit.create(TheLifeYouCanSaveService.class);
        return theLifeYouCanSaveService;
    }

    @NonNull
    @Provides
    @Singleton
    public Gson provideGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                .create();
        return gson;
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(Gson gson, OkHttpClient okHttpClient) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient);
        return retrofitBuilder;
    }
}
