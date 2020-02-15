package com.example.flickrfindr.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrfindr.service.model.BookMark
import com.example.flickrfindr.service.repository.FlickrItemRepository
import com.example.flickrfindr.utils.LoadingState
import kotlinx.coroutines.launch
import java.lang.Exception

class FlickrItemViewModel(private val flickrItemRepository: FlickrItemRepository): ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState


    var currentlySearchingWord = "cats"
    var currentSearchAmount = MutableLiveData<Int>()

    val data = flickrItemRepository.data
    val preferredData = flickrItemRepository.preferredData
    val bookmarks =  flickrItemRepository.bookMarkData

    init {
        //Initially trigger a data refresh
        currentSearchAmount.value = 25
        fetchData()
    }

    fun getPreferredItems(key: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                currentSearchAmount.value?.let {
                    flickrItemRepository.getKeyWordData(key,it)
                }
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                Log.e("Error", e.stackTrace.toString())
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    fun addBookMark(bookMark: BookMark) {
        flickrItemRepository.addBookMark(bookMark)
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                currentSearchAmount.value?.let {
                    flickrItemRepository.getData(it)
                }
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                Log.e("Error", e.stackTrace.toString())
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        flickrItemRepository.clearDisposables()
    }
}