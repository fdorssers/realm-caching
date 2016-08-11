package com.example.realmcaching.injection;

import com.example.realmcaching.ui.repositories.RepositoriesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by frank on 1-6-2016.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(RepositoriesFragment repositoriesFragment);
}
