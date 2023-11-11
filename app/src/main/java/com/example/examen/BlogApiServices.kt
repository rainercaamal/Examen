package com.example.examen

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogApiServices {
    //@GET("/BlogEntry")
    //fun getAllBlogEntries(): Call<List<BlogEntry>>

    @GET("/BlogEntry/Search")
    fun searchBlogEntries(@Query("searchTerm") searchTerm: String): Call<List<BlogEntry>>

}