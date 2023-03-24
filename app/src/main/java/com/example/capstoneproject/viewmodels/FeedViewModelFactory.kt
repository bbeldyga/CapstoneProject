package com.example.capstoneproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.interfaces.NewsAPI

class FeedViewModelFactory(private val newsAPI: NewsAPI): ViewModelProvider.Factory  {
    override fun <T: ViewModel> create (modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java))
            return FeedViewModel(newsAPI) as T
        throw java.lang.IllegalArgumentException("Unknown View Model")
    }
}