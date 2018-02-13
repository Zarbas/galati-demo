package com.demo.galatidemo.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.demo.galatidemo.entity.User;

import java.util.List;

/**
 * Interface for database access to retrieve our users from the database
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> user);

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> findById(int id);
}
