package com.example.realmcaching.api;

import com.example.realmcaching.model.entity.Repository;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by frank on 1-6-2016.
 */

public interface GithubApi {
    @Headers("User-Agent: Realm-Cache")
    @GET("/users/square/repos?per_page=15")
    Observable<List<Repository>> getRepositories();
}
