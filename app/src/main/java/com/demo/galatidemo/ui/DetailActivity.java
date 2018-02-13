package com.demo.galatidemo.ui;

import android.arch.lifecycle.LiveData;
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
import com.demo.galatidemo.databinding.ActivityDetailBinding;
import com.demo.galatidemo.entity.Comment;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.User;
import com.demo.galatidemo.ui.detail.DetailViewModel;

import java.util.List;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {

    public static final String ARG_POST_ID = "DetailActivity.ARG_POST_ID";
    public static final String ARG_USER_ID = "DetailActivity.ARG_USER_ID";

    // I need a custom DataBindingComponent to display user image from the network through Glide
    DataBindingComponent dataBindingComponent = new ActivityDataBindingComponent(this);

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail, dataBindingComponent);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewModel();
        LiveData<Post> post = detailViewModel.getPost();
        post.observe(this, new Observer<Post>() {
            @Override
            public void onChanged(@Nullable Post post) {
                binding.content.setPost(post);
            }
        });

        LiveData<User> user = detailViewModel.getUser();
        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                binding.content.setUser(user);
            }
        });

        LiveData<List<Comment>> comments = detailViewModel.getComments();
        comments.observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                if(comments != null) {
                    binding.content.setComments(String.valueOf(comments.size()));
                } else {
                    binding.content.setComments(String.valueOf(0));
                }
            }
        });
    }

    // get an instance of the ViewModel andupdate the ids inside to fetch the data from the database
    private void initViewModel() {
        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);
        Intent intent = getIntent();

        if(intent.hasExtra(ARG_POST_ID) && intent.hasExtra(ARG_USER_ID)) {
            detailViewModel.setId(intent.getIntExtra(ARG_POST_ID, 0), intent.getIntExtra(ARG_USER_ID, 0));
        }
    }
}
