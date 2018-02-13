package com.demo.galatidemo.entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * The Room entity that represents a single user. We don't need any other data except id, name and
 * email and there is no reason in a simple app to cache anything else.
 */

@Entity(primaryKeys = "id")
public class User {

    @NonNull
    private final int id;
    private final String name;
    private final String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
