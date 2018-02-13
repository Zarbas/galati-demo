package com.demo.galatidemo.ui.post;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.galatidemo.R;
import com.demo.galatidemo.databinding.PostItemBinding;
import com.demo.galatidemo.entity.Post;
import com.demo.galatidemo.entity.UserPost;

import java.util.List;

/**
 * Adapter for the MainActivity's RecyclerView. It uses UserPost because we need to merge the user's
 * email inside our Post to display the image.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    @Nullable
    private List<UserPost> items;

    // Data binding component is an instance of ActivityDataBindingComponent to load and display
    // the image through Glide
    private final DataBindingComponent dataBindingComponent;
    private final PostClickCallback callback;

    public PostAdapter(DataBindingComponent dataBindingComponent, PostClickCallback callback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final PostItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.post_item, parent, false,
                        dataBindingComponent);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = binding.getPost();

                if (post != null && callback != null) {
                    callback.onClick(post);
                }
            }
        });

        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.binding.setPost(items.get(position));

        holder.binding.executePendingBindings();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        PostItemBinding binding;

        public PostViewHolder(PostItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    @Override
    public int getItemCount() {
        if(items == null) {
            return 0;
        }

        return items.size();
    }

    public void setPosts(List<UserPost> posts) {
        this.items = posts;
        notifyDataSetChanged();
    }

    public interface PostClickCallback {
        void onClick(Post post);
    }
}
