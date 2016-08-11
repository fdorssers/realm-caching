package com.example.realmcaching.injection;

import com.example.realmcaching.BuildConfig;
import com.example.realmcaching.api.GithubApi;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by frank on 1-6-2016.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

    @Provides
    @Singleton
    public OkClient provideOkClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
    }

    @Provides
    @Singleton
    public GsonConverter provideGsonConverter(Gson gson) {
        return new GsonConverter(gson);
    }

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(GsonConverter gsonConverter, OkClient okClient) {
        return new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setClient(okClient)
                .setConverter(gsonConverter)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .build();
    }

    @Provides
    @Singleton
    public GithubApi provideGithubApi(RestAdapter restAdapter) {
        return restAdapter.create(GithubApi.class);
    }
}
