package com.example.capstoneproject.repository

import com.example.capstoneproject.database.UserPreferencesDatabase
import com.example.capstoneproject.dataobjects.AppContainer
import com.example.capstoneproject.interfaces.UserPreferencesDAO

class NewsRepository(private val userPreferencesDatabase: UserPreferencesDatabase) {

    private lateinit var appContainer: AppContainer

    suspend fun getNews(country: String, category: String, apiKey: String) =
        appContainer.newsAPI.getArticles(country, category, apiKey)

}