package com.example.examen

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BlogEntryController {
    @POST("/BlogEntry")
    fun createBlogEntry(@Body entry: BlogEntryy): Call<Void>
}