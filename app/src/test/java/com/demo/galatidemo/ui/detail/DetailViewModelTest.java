package com.demo.galatidemo.ui.detail;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.demo.galatidemo.repository.DataRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class DetailViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private DetailViewModel detailViewModel;
    private DataRepository dataRepository;

    @Before
    public void setup() {
        dataRepository = mock(DataRepository.class);
        detailViewModel = new DetailViewModel(dataRepository);
    }

    @Test
    public void testNull() {
        assertThat(detailViewModel.getPost(), notNullValue());
        assertThat(detailViewModel.getUser(), notNullValue());
        assertThat(detailViewModel.getComments(), notNullValue());
    }

    @Test
    public void dontFetchWithoutObservers() {
        detailViewModel.setId(1, 1);
        verify(dataRepository, never()).getPost(anyInt());
        verify(dataRepository, never()).getUser(anyInt());
        verify(dataRepository, never()).getComments(anyInt());
    }

    @Test
    public void fetchWhenObserved() {
        ArgumentCaptor<Integer> id = ArgumentCaptor.forClass(Integer.class);

        detailViewModel.setId(1, 1);

        detailViewModel.getPost().observeForever(mock(Observer.class));
        verify(dataRepository, times(1)).getPost(id.capture());
        assertThat(id.getValue(), is(1));

        detailViewModel.getUser().observeForever(mock(Observer.class));
        verify(dataRepository, times(1)).getUser(id.capture());
        assertThat(id.getValue(), is(1));

        detailViewModel.getComments().observeForever(mock(Observer.class));
        verify(dataRepository, times(1)).getComments(id.capture());
        assertThat(id.getValue(), is(1));
    }

    @Test
    public void changeWhileObserved() {
        ArgumentCaptor<Integer> postId = ArgumentCaptor.forClass(Integer.class);

        detailViewModel.getPost().observeForever(mock(Observer.class));

        detailViewModel.setId(1, 1);
        detailViewModel.setId(2, 1);

        verify(dataRepository, times(2)).getPost(postId.capture());
        assertThat(postId.getAllValues(), is(Arrays.asList(1, 2)));
    }

}