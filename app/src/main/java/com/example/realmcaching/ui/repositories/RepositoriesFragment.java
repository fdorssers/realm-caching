package com.example.realmcaching.ui.repositories;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.realmcaching.R;
import com.example.realmcaching.model.entity.Repository;
import com.example.realmcaching.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class RepositoriesFragment extends BaseFragment implements RepositoriesView {

    @Inject RepositoriesPresenter repositoriesPresenter;
    @Inject RepositoriesAdapter repositoriesAdapter;

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.image_message) ImageView messageImage;
    @BindView(R.id.swipe_to_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.layout_message) View messageView;
    @BindView(R.id.text_message) TextView messageText;
    @BindView(R.id.progress) ProgressBar progressBar;


    public RepositoriesFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews();
        repositoriesPresenter.attachView(this);
        repositoriesPresenter.loadRepositories(false);
    }

    private void setUpViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(repositoriesAdapter);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> repositoriesPresenter.loadRepositories(true));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        repositoriesPresenter.detachView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mvp_recyclerview;
    }

    @Override
    protected void injectDependencies() {
        getApplicationComponent().inject(this);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (pullToRefresh) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            messageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showData(List<Repository> repositories) {
        repositoriesAdapter.setData(repositories);
        repositoriesAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        messageView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(boolean pullToRefresh) {
        Timber.d("showError (" + pullToRefresh + ")");
        swipeRefreshLayout.setRefreshing(false);
        if (pullToRefresh) {
            Snackbar.make(getView(), R.string.text_error_loading_repos_short, Snackbar.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.GONE);
            messageText.setText(R.string.text_error_loading_repos);
            messageImage.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_gray_120dp);
            messageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showEmpty() {
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        messageText.setText(R.string.text_no_repos);
        messageImage.setImageResource(R.drawable.ic_empty_glass_gray_120dp);
        messageView.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.button_message)
    public void onMessageButtonClicked() {
        Timber.d("Button clicked");
        repositoriesPresenter.loadRepositories(false);
    }
}
