package com.example.realmcaching.injection;

import android.app.Application;
import android.content.Context;

import com.example.realmcaching.api.GithubApi;
import com.example.realmcaching.model.RepositoryModel;
import com.example.realmcaching.model.RepositoryModelRealmCache;
import com.example.realmcaching.ui.repositories.RepositoriesAdapter;
import com.example.realmcaching.ui.repositories.RepositoriesPresenter;
import com.example.realmcaching.utility.ImageLoader;
import com.example.realmcaching.utility.ImageLoaderPicasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by frank on 1-6-2016.
 */
@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader(Context context) {
        return new ImageLoaderPicasso(context);
    }

    @Provides
    @Singleton
    public RepositoryModel provideRepositoryModel(GithubApi githubApi) {
        return new RepositoryModelRealmCache(githubApi);
    }

    @Provides
    @Singleton
    public RepositoriesAdapter provideRepositoriesAdapter(ImageLoader imageLoader) {
        return new RepositoriesAdapter(imageLoader);
    }

    @Provides
    @Singleton
    public RepositoriesPresenter provideRepositoriesPresenter(RepositoryModel repositoryModel) {
        return new RepositoriesPresenter(repositoryModel);
    }
}
