package com.example.flickrfindr.service.repository

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.flickrfindr.service.model.PhotosResponse



interface FlickrItemApi {

    @GET("services/rest/")
    fun getAll(@Query("method") method: String,
               @Query("api_key") api_key: String,
               @Query("tags") tags: String,
               @Query("format") format: String,
               @Query("nojsoncallback") nojsoncallback: String,
               @Query("per_page") per_page: String = "25"
    ): Observable<PhotosResponse>

}