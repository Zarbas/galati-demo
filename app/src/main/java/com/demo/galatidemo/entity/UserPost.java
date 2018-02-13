package com.demo.galatidemo.entity;

/**
 * The Room entity that represents a single post with the related user email. I'm using it only to
 * display both text and images in the MainActivity's RecyclerView
 */
public class UserPost extends Post {

    private String email;

    public UserPost(int id, int userId, String title, String body, String email) {
        super(id, userId, title, body);

        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
