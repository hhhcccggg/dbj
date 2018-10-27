package com.zwdbj.server.mobileapi.model;

public class EntityKeyModel<T> {
    T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public EntityKeyModel(T id) {
        this.id = id;
    }

    public EntityKeyModel() {

    }

}
