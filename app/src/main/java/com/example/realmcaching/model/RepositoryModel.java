package com.example.realmcaching.model;

import com.example.realmcaching.model.entity.Repository;

import java.util.List;

import rx.Observable;

/**
 * Created by frank on 1-6-2016.
 */

public interface RepositoryModel {

    Observable<List<Repository>> getRepositories(boolean withCache);
}
