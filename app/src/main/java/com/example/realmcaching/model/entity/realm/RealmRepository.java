package com.example.realmcaching.model.entity.realm;

import com.example.realmcaching.model.entity.Repository;

import io.realm.RealmObject;

/**
 * Created by frank on 2-6-2016.
 */

public class RealmRepository extends RealmObject {

    public String name;
    public String fullName;
    public RealmOwner owner;

    public RealmRepository() {

    }

    public RealmRepository(Repository repository) {
        name = repository.name;
        fullName = repository.fullName;
        owner = new RealmOwner(repository.owner);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RealmOwner getOwner() {
        return owner;
    }

    public void setOwner(RealmOwner owner) {
        this.owner = owner;
    }

    public Repository toEntity() {
        Repository repository = new Repository();
        repository.name = getName();
        repository.fullName = getFullName();
        repository.owner = getOwner().toEntity();
        return repository;
    }
}
