package com.demo.galatidemo.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.UserPost;

import java.util.List;

/**
 * Interface for database access to retrieve our posts from the database
 */
@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> post);

    /**
     * this function join the post and the user table to be able to retrieve the user email for
     * every post. It is used only to display both text and images in the Main Activity's
     * RecyclerView
     */

    @Query("SELECT post.*, user.email FROM post JOIN user ON post.userId = user.id")
    LiveData<List<UserPost>> allWithUsers();

    @Query("SELECT * FROM post WHERE id = :id")
    LiveData<Post> findById(int id);
}
