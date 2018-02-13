package com.demo.galatidemo.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

/**
 * The room entity that represents a single comment. We don't need the name of the sender or the
 * text of the comment, so I decided to store only the useful data and count the resulting rows
 * to get the number of comments for each post.
 */

@Entity(primaryKeys = "id",
        foreignKeys = @ForeignKey(entity = Post.class,
        parentColumns = "id",
        childColumns = "postId"),
        indices = @Index("postId"))
public class Comment {
    @NonNull
    private int id;
    @NonNull
    private int postId;

    public Comment(int id, int postId) {
        this.id = id;
        this.postId = postId;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public int getPostId() {
        return postId;
    }
}
