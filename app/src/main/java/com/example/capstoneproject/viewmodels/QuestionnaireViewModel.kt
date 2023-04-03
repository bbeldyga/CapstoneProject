package com.example.capstoneproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.dataobjects.UserPreferences
import com.example.capstoneproject.interfaces.UserPreferencesDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Questionnaire Screen Data and Logic
 */
class QuestionnaireViewModel(private val userPreferencesDAO: UserPreferencesDAO)
    : ViewModel() {

    private val email = FirebaseAuth.getInstance().currentUser?.email

    fun saveResults(userNewsPrefs: MutableList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            val userPreferences = UserPreferences(email!!, userNewsPrefs[0],
                userNewsPrefs[1], userNewsPrefs[2], userNewsPrefs[3], userNewsPrefs[4],
                userNewsPrefs[5], userNewsPrefs[6])

            userPreferencesDAO.insert(userPreferences)
        }
    }

    companion object {
        fun provideFactory(
            userPreferencesDAO: UserPreferencesDAO
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuestionnaireViewModel(userPreferencesDAO) as T
            }
        }
    }
}