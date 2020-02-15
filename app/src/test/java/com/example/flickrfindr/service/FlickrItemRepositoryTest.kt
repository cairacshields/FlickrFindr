package com.example.flickrfindr.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.flickrfindr.service.database.BookMarkDao
import com.example.flickrfindr.service.database.FlickrItemDao
import com.example.flickrfindr.service.model.Photo
import com.example.flickrfindr.service.repository.FlickrItemApi
import com.example.flickrfindr.service.repository.FlickrItemRepository
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import rx.schedulers.TestScheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule


class FlickrItemRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockFlickrItemApi: FlickrItemApi = mock()
    private val mockFlickrItemDao: FlickrItemDao = mock()
    private val mockBookMarkDao: BookMarkDao = mock()

    private val testScheduler = TestScheduler()
    private val mockRepository: FlickrItemRepository = mock()
    private lateinit var testObject: FlickrItemRepository
    private val mockLiveData: LiveData<List<Photo>> = mock()

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        testObject = FlickrItemRepository(
            mockFlickrItemApi,
            mockFlickrItemDao,
            mockBookMarkDao
        )

        whenever(mockFlickrItemDao.findAll()).thenReturn(mockLiveData)
    }

    @Test
    fun `getData checks database`() {
        mockRepository.getData(any())
        testScheduler.triggerActions()
        verify(mockFlickrItemDao).findAll()
        assertEquals(mockFlickrItemDao.findAll(), mockLiveData)
    }


}