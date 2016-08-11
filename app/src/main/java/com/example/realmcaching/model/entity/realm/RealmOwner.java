package com.example.realmcaching.model.entity.realm;

import com.example.realmcaching.model.entity.Owner;

import io.realm.RealmObject;

/**
 * Created by frank on 2-6-2016.
 */

public class RealmOwner extends RealmObject {

    private String login;
    private String avatarUrl;

    public RealmOwner() {

    }

    public RealmOwner(Owner owner) {
        login = owner.login;
        avatarUrl = owner.avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Owner toEntity() {
        Owner owner = new Owner();
        owner.login = getLogin();
        owner.avatarUrl = getAvatarUrl();
        return owner;
    }
}
