package com.example.realmcaching.model;

import com.example.realmcaching.api.GithubApi;
import com.example.realmcaching.model.entity.Repository;
import com.example.realmcaching.model.entity.realm.RealmRepository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by frank on 2-6-2016.
 */

public class RepositoryModelRealmCache implements RepositoryModel {

    private GithubApi githubApi;
    private Realm realm;

    public RepositoryModelRealmCache(GithubApi githubApi) {
        this.githubApi = githubApi;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<Repository>> getRepositories(boolean withCache) {
        Observable<List<Repository>> remote = getRemoteObservable();

        if (!withCache) return remote;

        Observable<List<Repository>> local = getLocalObservable();
        return Observable.concat(local, remote);
    }

    private Observable<List<Repository>> getLocalObservable() {
        return realm.where(RealmRepository.class).findAll().asObservable()
                .map(realmRepositories -> {
                    List<Repository> repositories = new ArrayList<>();
                    for (RealmRepository realmRepository : realmRepositories) {
                        repositories.add(realmRepository.toEntity());
                    }
                    return repositories;
                });
    }

    private Observable<List<Repository>> getRemoteObservable() {
        return githubApi.getRepositories()
                .doOnNext(repositories -> {
                    Timber.d("Storing remote data");
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(realm1 -> {
                        realm1.where(RealmRepository.class).findAll().deleteAllFromRealm();
                        for (Repository repository : repositories) {
                            realm1.copyToRealm(new RealmRepository(repository));
                        }
                    });
                    realm.close();
                });
    }
}

