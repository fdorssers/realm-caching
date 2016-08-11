package com.example.realmcaching.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by frank on 1-6-2016.
 */

public class Owner {

    public String login;
    @SerializedName("avatar_url")
    public String avatarUrl;

    @Override
    public String toString() {
        return "Owner{" +
                "login='" + login + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
