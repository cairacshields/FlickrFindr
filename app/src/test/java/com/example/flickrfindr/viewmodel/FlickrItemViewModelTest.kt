package com.example.flickrfindr.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.flickrfindr.service.repository.FlickrItemRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rx.schedulers.TestScheduler

class FlickrItemViewModelTest {
    private val testScheduler = TestScheduler()
    private lateinit var testObject: FlickrItemViewModel
    private val viewModelMock: FlickrItemViewModel = mock()
    private val mockRepository: FlickrItemRepository = mock()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        testObject = FlickrItemViewModel(
            mockRepository
        )

    }

    @Test
    fun `fetchData triggers liveData`() {
        viewModelMock.fetchData()
        testScheduler.triggerActions()
        verify(mockRepository).data
    }

    @Test
    fun `getPreferredItems triggers liveData`() {
        viewModelMock.getPreferredItems(any())
        testScheduler.triggerActions()
        verify(mockRepository).preferredData
    }
}