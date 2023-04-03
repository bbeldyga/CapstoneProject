package com.example.capstoneproject.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Sign In Screen Data and Logic
 */
class SignInViewModel(): ViewModel() {

    var email = ""
    var password = ""

    private var currentUserValid = false

    private var _notRegistered = MutableLiveData(false)
    val notRegistered: LiveData<Boolean> get() = _notRegistered

    private var _signInAttempt = MutableLiveData(false)
    val signInAttempt: LiveData<Boolean> get() = _signInAttempt

    private var _signInInvalid = MutableLiveData(false)
    val signInInvalid: LiveData<Boolean> get() = _signInInvalid

    private var _emptyFields = MutableLiveData(false)
    val emptyFields: LiveData<Boolean> get() = _emptyFields

    init {
        checkCurrentUser()
    }

//    fun signIn() {
//        if (email.isNotEmpty() && password.isNotEmpty()) {
//            _signInAttempt.value = true
//        }
//    }

    fun signIn() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                val auth = async { authorizeUser() }

                _signInAttempt.value = auth.await() != null
            }
        } else {
            _emptyFields.value = true
        }
    }

    private suspend fun authorizeUser(): AuthResult? {
        return try {
            val result = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password).await()

            result
        } catch (e: Exception) {
            Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
            null
        }
    }

    fun notRegisteredTextClicked() {
        _notRegistered.value = true
    }

    fun getCurrentUserValid(): Boolean {
        return currentUserValid
    }

    private fun checkCurrentUser() {
         if (FirebaseAuth.getInstance().currentUser != null) {
            currentUserValid = true
         }
    }
}