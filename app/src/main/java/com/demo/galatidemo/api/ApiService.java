package com.demo.galatidemo.api;

import com.demo.galatidemo.entity.Comment;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface used by Retrofit to retrieve data from the webservice.
 * The retrofit
 */

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("comments")
    Call<List<Comment>> getComments();
}
