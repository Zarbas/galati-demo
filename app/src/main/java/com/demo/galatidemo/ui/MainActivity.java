package com.demo.galatidemo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.demo.galatidemo.R;
import com.demo.galatidemo.binding.ActivityDataBindingComponent;
import com.demo.galatidemo.databinding.ActivityMainBinding;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.UserPost;
import com.demo.galatidemo.ui.post.PostAdapter;
import com.demo.galatidemo.ui.post.PostViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    // I need a custom DataBindingComponent to display user images from the network through Glide
    private DataBindingComponent dataBindingComponent = new ActivityDataBindingComponent(this);

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityMainBinding binding;
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        // to keep things simple I start a new activity from the listener (it is the only place
        // where I need to do that). With more complex Apps it would be helpful to create a
        // NavigationController to manage the navigation and to inject it where needed.
        final PostAdapter adapter = new PostAdapter(dataBindingComponent, new PostAdapter.PostClickCallback() {
            @Override
            public void onClick(Post post) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                // I send both postId and userId because is easier to retrieve data (I don't have to
                // wait for Post data to retrieve the User)
                intent.putExtra(DetailActivity.ARG_POST_ID, post.getId());
                intent.putExtra(DetailActivity.ARG_USER_ID, post.getUserId());
                startActivity(intent);
            }
        });

        binding.content.rvPostList.setAdapter(adapter);

        postViewModel = ViewModelProviders.of(this, viewModelFactory).get(PostViewModel.class);
        postViewModel.getPosts().observe(this, new Observer<List<UserPost>>() {
            @Override
            public void onChanged(@Nullable List<UserPost> posts) {
                adapter.setPosts(posts);
            }
        });
    }
}
