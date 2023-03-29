package com.example.capstoneproject.dataobjects

import android.content.Context
import androidx.room.Room
import com.example.capstoneproject.database.UserPreferencesDatabase
import com.example.capstoneproject.interfaces.NewsAPI
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    val newsAPI: NewsAPI = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    val userPreferencesDatabase = Room.databaseBuilder(
        context.applicationContext,
        UserPreferencesDatabase::class.java,
        "userPreferencesDatabase"
    ).build()

    val userPreferencesDAO = userPreferencesDatabase.userPreferencesDAO

    val firebaseAuth = FirebaseAuth.getInstance()
}