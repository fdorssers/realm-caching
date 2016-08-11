package com.example.realmcaching.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by frank on 1-6-2016.
 */

public class Repository {

    public String name;
    @SerializedName("full_name")
    public String fullName;
    public Owner owner;

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", owner=" + owner +
                '}';
    }
}
