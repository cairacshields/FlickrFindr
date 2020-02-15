package com.example.flickrfindr.service.model


data class Photos (
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photo: ArrayList<Photo>

)