package com.example.capstoneproject

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface NewsAPI {
    @GET("top-headlines")
    suspend fun getArticles(@Query("country") country: String,
                    @Query("category") category: String,
                    @Query("apiKey") apiKey: String) : Response<NewsResponse>

//    companion object {
//        var BASE_URL = "https://newsapi.org/v2/"
//
//        fun create() : NewsAPI {
//
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(BASE_URL)
//                .build()
//            return retrofit.create(NewsAPI::class.java)
//        }
//    }
}
