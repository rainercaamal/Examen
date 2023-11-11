package com.example.examen

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogSearchApiService {

        @GET("BlogEntry/Search")
        fun searchBlogEntries(@Query("searchTerm") searchTerm: String): Call<List<BlogEntry>>

}