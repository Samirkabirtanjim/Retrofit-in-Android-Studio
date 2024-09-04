package com.example.newsapi

import okhttp3.Response
import retrofit2.http.GET


interface AlbumServiceInterface {
    @GET("/albums")
    suspend fun getAlbums(): retrofit2.Response<AlbumItemArray>
}