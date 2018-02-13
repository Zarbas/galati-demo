package com.demo.galatidemo.ui.post;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.demo.galatidemo.entity.UserPost;
import com.demo.galatidemo.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel for the MainActivity (used to update the RecyclerView).
 */
public class PostViewModel extends ViewModel {
    @VisibleForTesting
    private final LiveData<List<UserPost>> posts;

    @Inject
    public PostViewModel(DataRepository dataRepository) {
        posts = dataRepository.getPosts();
    }

    public LiveData<List<UserPost>> getPosts() {
        return posts;
    }

}
