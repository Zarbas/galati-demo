package com.demo.galatidemo.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.demo.galatidemo.entity.Comment;

import java.util.List;

/**
 * Interface for database access to retrieve our comments from the database
 */
@Dao
public interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Comment> comment);

    @Query("SELECT * FROM comment WHERE postId = :postId")
    LiveData<List<Comment>> findByPostId(int postId);
}
