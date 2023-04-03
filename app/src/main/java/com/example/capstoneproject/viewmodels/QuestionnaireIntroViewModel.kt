package com.example.capstoneproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Questionnaire Intro Screen Data and Logic
 */
class QuestionnaireIntroViewModel: ViewModel() {

    private var _proceed = MutableLiveData(false)
    val proceed: LiveData<Boolean> get() = _proceed

    private var _skip = MutableLiveData(false)
    val skip: LiveData<Boolean> get() = _skip

    fun proceedButtonClicked() {
        _proceed.value = true
    }
    fun skipButtonClicked() {
        _skip.value = true
    }
}