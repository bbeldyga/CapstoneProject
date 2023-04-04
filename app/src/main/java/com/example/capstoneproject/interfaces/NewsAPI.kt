package com.example.capstoneproject.interfaces

import com.example.capstoneproject.dataobjects.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getArticles(@Query("country") country: String,
                    @Query("category") category: String,
                    @Query("apiKey") apiKey: String) : Response<NewsResponse>
}
