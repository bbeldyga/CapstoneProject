package com.example.capstoneproject.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.*

/**
 * Sign In Screen Data and Logic
 */
class SignInViewModel(): ViewModel() {

    var email = ""
    var password = ""

    private val firebaseAuth = FirebaseAuth.getInstance()

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

    fun signIn() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _signInAttempt.value = true
        }
    }
//            val job = viewModelScope.launch(Dispatchers.Default) {
//                val auth = async { authorizeUser() }
//
//                _signInAttempt.value = auth.await() != null
//            }
//        } else {
//            _emptyFields.value = true
//        }
//    }

    fun notRegisteredTextClicked() {
        _notRegistered.value = true
    }

//    private suspend fun authorizeUser(): AuthResult? {
//        return try {
//            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//
//            result
//        } catch (e: Exception) {
//            Log.e("NewsAPI", "Failed to fetch news: ${e.message}")
//            null
//        }
//    }

    private fun checkCurrentUser() {
         if (firebaseAuth.currentUser != null) {
            currentUserValid = true
         }
    }

    fun getCurrentUserValid(): Boolean {
        return currentUserValid
    }
}