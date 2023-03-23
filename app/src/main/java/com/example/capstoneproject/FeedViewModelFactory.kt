package com.example.capstoneproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FeedViewModelFactory(private val newsAPI: NewsAPI): ViewModelProvider.Factory  {
    override fun <T: ViewModel> create (modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java))
            return FeedViewModel(newsAPI) as T
        throw java.lang.IllegalArgumentException("Unknown View Model")
    }
}