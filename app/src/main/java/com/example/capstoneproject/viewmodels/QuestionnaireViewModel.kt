package com.example.capstoneproject.viewmodels

import androidx.lifecycle.*
import com.example.capstoneproject.dataobjects.UserPreferences
import com.example.capstoneproject.interfaces.UserPreferencesDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Questionnaire Screen Data and Logic
 */
class QuestionnaireViewModel(private val userPreferencesDAO: UserPreferencesDAO)
    : ViewModel() {

    private val email = FirebaseAuth.getInstance().currentUser?.email
    private val questionnaireData = mutableListOf("General", "Technology", "Entertainment", "Sports", "Business", "Health", "Science")
    private var userNewsPrefs = mutableListOf(3, 3, 3, 3, 3, 3, 3)
    private var questionnaireCount = 0

    private val errorHandler = CoroutineExceptionHandler {_, exception ->
        exception.printStackTrace() }

    private var _topic = MutableLiveData<String>()
    val topic: LiveData<String> get() = _topic

    private var _finished = MutableLiveData<Boolean>(false)
    val finished: LiveData<Boolean> get() = _finished

    private var _skip = MutableLiveData<Boolean>(false)
    val skip: LiveData<Boolean> get() = _skip

    init {
        _topic.value = questionnaireData[questionnaireCount]
    }

    private fun saveResults() {
        viewModelScope.launch(Dispatchers.Main + errorHandler) {
            val userPreferences = UserPreferences(email!!, userNewsPrefs[0].toFloat(),
                userNewsPrefs[1].toFloat(), userNewsPrefs[2].toFloat(), userNewsPrefs[3].toFloat(),
                userNewsPrefs[4].toFloat(), userNewsPrefs[5].toFloat(), userNewsPrefs[6].toFloat())

            userPreferencesDAO.insert(userPreferences)
            _finished.value = true
        }
    }

    fun veryInterestedButtonClicked() {
        recordResponse(5)
    }

    fun interestedButtonClicked() {
        recordResponse(4)
    }

    fun noPreferenceButtonClicked() {
        recordResponse(3)
    }

    fun uninterestedButtonClicked() {
        recordResponse(2)
    }

    fun veryUninterestedButtonClicked() {
        recordResponse(1)
    }

    private fun recordResponse(response: Int) {
        when (questionnaireCount) {
                 in 0..5 -> {
                    userNewsPrefs[questionnaireCount++] = response
                    _topic.value = questionnaireData[questionnaireCount]
                }
                6 -> {
                    userNewsPrefs[questionnaireCount] = response
                    saveResults()
                }
        }
    }

    fun skipButtonClicked() {
        _skip.value = true
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