package com.demo.galatidemo.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.demo.galatidemo.entity.Comment;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ApiServiceTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ApiService apiService;

    private MockWebServer mockWebServer;

    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        apiService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getUsers() throws IOException, InterruptedException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/users.json");
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        mockWebServer.enqueue(new MockResponse().setBody(source.readString(StandardCharsets.UTF_8)));

        User user = apiService.getUsers().execute().body().get(0);

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/users"));

        assertThat(user, notNullValue());
        assertThat(user.getId(), is(1));
    }

    @Test
    public void getPosts() throws IOException, InterruptedException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/posts.json");
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        mockWebServer.enqueue(new MockResponse().setBody(source.readString(StandardCharsets.UTF_8)));

        Post post = apiService.getPosts().execute().body().get(0);

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/posts"));

        assertThat(post, notNullValue());
        assertThat(post.getId(), is(1));
        assertThat(post.getUserId(), is(1));
    }

    @Test
    public void getComments() throws IOException, InterruptedException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/comments.json");
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        mockWebServer.enqueue(new MockResponse().setBody(source.readString(StandardCharsets.UTF_8)));

        List<Comment> comment = apiService.getComments().execute().body();

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/comments"));

        assertThat(comment, notNullValue());
        assertThat(comment.size(), is(2));
        assertThat(comment.get(0), notNullValue());
        assertThat(comment.get(0).getId(), is(1));
        assertThat(comment.get(1), notNullValue());
        assertThat(comment.get(1).getId(), is(2));
    }

}
