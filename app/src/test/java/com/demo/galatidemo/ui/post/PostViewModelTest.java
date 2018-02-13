package com.demo.galatidemo.ui.post;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.demo.galatidemo.repository.DataRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class PostViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private PostViewModel postViewModel;
    private DataRepository dataRepository;

    @Before
    public void setup() {
        dataRepository = mock(DataRepository.class);
        postViewModel = new PostViewModel(dataRepository);
    }


    @Test
    public void testNull() {
        assertThat(postViewModel.getPosts(), nullValue());
        verify(dataRepository).getPosts();
    }

    @Test
    public void fetch() {
        verify(dataRepository).getPosts();
    }

}