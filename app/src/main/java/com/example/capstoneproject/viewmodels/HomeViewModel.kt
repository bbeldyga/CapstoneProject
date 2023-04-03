package com.example.capstoneproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Home Screen Data and Logic
 */
class HomeViewModel: ViewModel() {

    private var _feed = MutableLiveData(false)
    val feed: LiveData<Boolean> get() = _feed

    private var _settings = MutableLiveData(false)
    val settings: LiveData<Boolean> get() = _settings

    fun feedButtonClicked() {
        _feed.value = true
    }

    fun settingsButtonClicked() {
        _settings.value = true
    }
}