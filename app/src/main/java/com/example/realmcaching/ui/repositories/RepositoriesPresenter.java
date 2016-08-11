package com.example.realmcaching.ui.repositories;

import com.example.realmcaching.model.RepositoryModel;
import com.example.realmcaching.model.entity.Repository;
import com.example.realmcaching.ui.base.BasePresenter;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by frank on 2-6-2016.
 */

public class RepositoriesPresenter extends BasePresenter<RepositoriesView> {

    private RepositoryModel repositoryModel;

    private Subscription subscription;

    public RepositoriesPresenter(RepositoryModel repositoryModel) {
        this.repositoryModel = repositoryModel;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void loadRepositories(final boolean pullToRefresh) {
        Timber.d("Loading repositories");
        checkViewAttached();
        getMvpView().showLoading(pullToRefresh);

        subscription = repositoryModel.getRepositories(!pullToRefresh)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("onError " + e.getLocalizedMessage(), e);
                        getMvpView().showError(pullToRefresh);
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        Timber.d("onNext " + repositories.toString());
                        if (repositories == null || repositories.isEmpty()) {
                            getMvpView().showEmpty();
                        } else {
                            getMvpView().showData(repositories);
                        }
                    }
                });
    }
}
