package com.demo.galatidemo.repository;

import android.arch.lifecycle.LiveData;

import com.demo.galatidemo.AppExecutors;
import com.demo.galatidemo.api.ApiService;
import com.demo.galatidemo.db.CommentDao;
import com.demo.galatidemo.db.PostDao;
import com.demo.galatidemo.db.UserDao;
import com.demo.galatidemo.entity.Comment;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.User;
import com.demo.galatidemo.entity.UserPost;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Used to abstract the data layer. Every method accessible to ViewModels return data from the
 * internal database to ensure a single source of truth. In a real application, it should also be
 * aware of network errors, retry fetch if it failed, and it should send back to ViewModels more
 * complex data with the state of the network calls (for example if it is still loading data) to
 * display more information for the user.
 */

@Singleton
public class DataRepository {
    private final ApiService apis;
    private final UserDao userDao;
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final AppExecutors appExecutors;

    @Inject
    public DataRepository(ApiService apiservice, UserDao userDao, PostDao postDao, CommentDao commentDao, AppExecutors appExecutors) {
        this.apis = apiservice;
        this.userDao = userDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.appExecutors = appExecutors;
    }

    // to keep the data layer simple (not enough time), I fetch the data to "update" our
    // database without checking if we really need it (not really because I fetch all the data
    // at the first call and there is no need to fetch them again if we restart the app).
    // This function should be call just once when the app starts.
    public LiveData<List<UserPost>> getPosts() {
        fetchData();

        return postDao.allWithUsers();
    }

    public LiveData<Post> getPost(int id) {
        return postDao.findById(id);
    }

    public LiveData<User> getUser(int id) {
        return userDao.findById(id);
    }

    public LiveData<List<Comment>> getComments(int postId) {
        return commentDao.findByPostId(postId);
    }

    // This function fetch all the data together because we need at least 2 calls to display
    // the title of the post and the user's image. I've included the third call here to keep
    // things simple (they are executed in background and the data become visible even if the
    // third call is still running)
    private void fetchData() {
        fetchUsers();
        fetchPosts();
        fetchComments();
    }

    private void fetchUsers() {
        appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call<List<User>> callUsers = apis.getUsers();
                try {
                    Response<List<User>> responseUsers = callUsers.execute();
                    List<User> users = responseUsers.body();
                    userDao.insertAll(users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchPosts() {
        appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Post>> callPosts = apis.getPosts();
                try {
                    Response<List<Post>> responsePosts = callPosts.execute();
                    List<Post> posts = responsePosts.body();
                    postDao.insertAll(posts);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchComments() {
        appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Comment>> callComments = apis.getComments();
                try {
                    Response<List<Comment>> responseComments = callComments.execute();
                    List<Comment> comments = responseComments.body();
                    commentDao.insertAll(comments);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}