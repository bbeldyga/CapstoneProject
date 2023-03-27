package com.example.capstoneproject.dataobjects

import android.content.Context
import com.example.capstoneproject.interfaces.NewsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    val newsAPI: NewsAPI = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)
}