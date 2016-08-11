package com.example.realmcaching.ui.repositories;

import com.example.realmcaching.model.entity.Repository;
import com.example.realmcaching.ui.base.MvpView;

import java.util.List;

/**
 * Created by frank on 2-6-2016.
 */

public interface RepositoriesView extends MvpView {

    void showData(List<Repository> repositories);

    void showLoading(boolean pullToRefresh);

    void showError(boolean pullToRefresh);

    void showEmpty();
}
