package com.example.examen

import retrofit2.Call
import retrofit2.http.GET

interface BlogApiService {
    @GET("/BlogEntry")
    fun getAllBlogEntries(): Call<List<BlogEntry>>

}