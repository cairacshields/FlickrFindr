package com.example.flickrfindr.service.repository

import androidx.lifecycle.MutableLiveData
import com.example.flickrfindr.service.database.BookMarkDao
import com.example.flickrfindr.service.database.FlickrItemDao
import com.example.flickrfindr.service.model.BookMark
import com.example.flickrfindr.service.model.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FlickrItemRepository(private val flickrItemApi: FlickrItemApi,
                           private val flickrItemDao: FlickrItemDao,
                           private val bookMarkDao: BookMarkDao) {

    private val compositeDisposable = CompositeDisposable()
    val data = flickrItemDao.findAll()
    val preferredData = MutableLiveData<List<Photo>>()
    val bookMarkData = bookMarkDao.findAll()

    //Check if db is empty, grab data from API if so
    fun getData(amount: Int) {
        if (data.value == null) {
            compositeDisposable.add(flickrItemApi.getAll(method = METHOD, api_key = API_KEY,
                tags = "cats", format = FORMAT, nojsoncallback = NO_JSON_CALLBACK, per_page = amount.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    storePhotosInDb(it.photos.photo)
                },{
                    it.printStackTrace()
                }))
        }
    }

    //Special case searching... don't really wanna store this data in database
    fun getKeyWordData(key: String, amount: Int) {
        compositeDisposable.add(flickrItemApi.getAll(method = METHOD, api_key = API_KEY,
            tags = key, format = FORMAT, nojsoncallback = NO_JSON_CALLBACK, per_page = amount.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                preferredData.postValue(it.photos.photo)
            },{
                it.printStackTrace()
            }))
    }

    fun addBookMark(bookMark: BookMark) {
        bookMarkDao.add(bookMark)
    }

    private fun storePhotosInDb(items: List<Photo>) {
        flickrItemDao.add(items)
    }

    fun clearDisposables() {
        compositeDisposable.dispose()
    }

    companion object {
        const val TAG = "FLICKR_ITEM_REPOSITORY"
        const val API_KEY = "1508443e49213ff84d566777dc211f2a"
        const val METHOD = "flickr.photos.search"
        const val FORMAT = "json"
        const val NO_JSON_CALLBACK = "1"
    }
}