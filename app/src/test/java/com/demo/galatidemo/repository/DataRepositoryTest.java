package com.demo.galatidemo.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.demo.galatidemo.api.ApiService;
import com.demo.galatidemo.db.CommentDao;
import com.demo.galatidemo.db.PostDao;
import com.demo.galatidemo.db.UserDao;
import com.demo.galatidemo.entity.Comment;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.User;
import com.demo.galatidemo.util.InstantAppExecutors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class DataRepositoryTest {
    private DataRepository repository;
    private PostDao postDao;
    private UserDao userDao;
    private CommentDao commentDao;
    private ApiService apiService;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        postDao = mock(PostDao.class);
        userDao = mock(UserDao.class);
        commentDao = mock(CommentDao.class);
        apiService = mock(ApiService.class);
        repository = new DataRepository(apiService, userDao, postDao, commentDao, new InstantAppExecutors());
    }

    @Test
    public void getPostTest() {
        MutableLiveData<Post> dbData = new MutableLiveData<>();
        Post post = new Post(1, 1, "test title", "test body");
        dbData.setValue(post);
        when(repository.getPost(1)).thenReturn(dbData);
        Observer<Post> observer = mock(Observer.class);
        repository.getPost(1).observeForever(observer);
        verify(apiService, never()).getPosts();
        verify(observer).onChanged(post);
    }

    @Test
    public void getUserTest() {
        MutableLiveData<User> dbData = new MutableLiveData<>();
        User user = new User(1, "test user", "user@test.com");
        dbData.setValue(user);
        when(repository.getUser(1)).thenReturn(dbData);
        Observer<User> observer = mock(Observer.class);
        repository.getUser(1).observeForever(observer);
        verify(apiService, never()).getUsers();
        verify(observer).onChanged(user);
    }

    @Test
    public void getCommentsTest() {
        MutableLiveData<List<Comment>> dbData = new MutableLiveData<>();
        Comment comment = new Comment(1, 1);
        List<Comment> list = new ArrayList<>();
        list.add(comment);
        dbData.setValue(list);
        when(repository.getComments(1)).thenReturn(dbData);
        Observer<List<Comment>> observer = mock(Observer.class);
        repository.getComments(1).observeForever(observer);
        verify(apiService, never()).getComments();
        verify(observer).onChanged(list);
    }

}