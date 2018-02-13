package com.demo.galatidemo.ui.detail;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.demo.galatidemo.entity.Comment;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.User;
import com.demo.galatidemo.repository.DataRepository;
import com.demo.galatidemo.util.EmptyLiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel for the DetailActivity.
 */
public class DetailViewModel extends ViewModel {
    // to keep things simple I've created 2 different MutableLiveData to trigger different switchMap
    // when the postId and userId are set (there is no need to wait for Post data because we
    // already have the userId)
    @VisibleForTesting
    final MutableLiveData<Integer> postId;
    final MutableLiveData<Integer> userId;
    private final LiveData<Post> post;
    private final LiveData<User> user;
    // the easiest way to have the number of comments is to extract them from the database and
    // check the size of the list (I can maintain the LiveData update from Room)
    private final LiveData<List<Comment>> comments;

    @Inject
    public DetailViewModel(final DataRepository dataRepository) {
        this.postId = new MutableLiveData<>();
        this.userId = new MutableLiveData<>();

        post = Transformations.switchMap(postId, new Function<Integer, LiveData<Post>>() {
            @Override
            public LiveData<Post> apply(Integer input) {
                if (input == 0) {
                    return EmptyLiveData.create();
                }
                return dataRepository.getPost(input);
            }
        });

        user = Transformations.switchMap(userId, new Function<Integer, LiveData<User>>() {
            @Override
            public LiveData<User> apply(Integer input) {
                if (input == 0) {
                    return EmptyLiveData.create();
                }
                return dataRepository.getUser(input);
            }
        });

        comments = Transformations.switchMap(postId, new Function<Integer, LiveData<List<Comment>>>() {
            @Override
            public LiveData<List<Comment>> apply(Integer input) {
                if (input == 0) {
                    return EmptyLiveData.create();
                }
                return dataRepository.getComments(input);
            }
        });
    }

    public LiveData<Post> getPost() {
        return post;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    @VisibleForTesting
    public void setId(int postId, int userId) {
        this.postId.setValue(postId);
        this.userId.setValue(userId);
    }

}
