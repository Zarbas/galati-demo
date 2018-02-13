package com.demo.galatidemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.demo.galatidemo.entity.Comment;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.User;

/**
 * Interface used by Room to create tables and Data Access Objects. I'm using a database because
 * usually one of the best practices is to cache some data to allow the app to work offline with
 * limited functions.
 */

@Database(entities = {User.class, Post.class, Comment.class}, version = 1)
public abstract class AppDB extends RoomDatabase {

    abstract public UserDao userDao();

    abstract public PostDao postDao();

    abstract public CommentDao commentDao();

}
