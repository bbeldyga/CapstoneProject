package com.example.capstoneproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * Settings Screen Data and Logic
 */
class SettingsViewModel: ViewModel() {

    private var _home = MutableLiveData(false)
    val home: LiveData<Boolean> get() = _home

    private var _questionnaire = MutableLiveData(false)
    val questionnaire: LiveData<Boolean> get() = _questionnaire

    private var _signOut = MutableLiveData(false)
    val signOut: LiveData<Boolean> get() = _signOut

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    init {
        _email.value = FirebaseAuth.getInstance().currentUser?.email
    }

    fun homeButtonClicked() {
        _home.value = true
    }

    fun questionnaireButtonClicked() {
        _questionnaire.value = true
    }

    fun signOutButtonClicked() {
        FirebaseAuth.getInstance().signOut()
        _signOut.value = true
    }
}