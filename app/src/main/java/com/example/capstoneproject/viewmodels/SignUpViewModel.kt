package com.example.capstoneproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Sign Up Screen Data and Logic
 */
class SignUpViewModel: ViewModel() {

    var email = ""
    var password = ""
    var retypedPassword = ""

    private var _alreadyRegistered = MutableLiveData(false)
    val alreadyRegistered: LiveData<Boolean> get() = _alreadyRegistered

    private var _emptyFields = MutableLiveData(false)
    val emptyFields: LiveData<Boolean> get() = _emptyFields

    private var _passwordMismatch = MutableLiveData(false)
    val passwordMismatch: LiveData<Boolean> get() = _passwordMismatch

    private var _signUp = MutableLiveData(false)
    val signUp: LiveData<Boolean> get() = _signUp

    fun signUp() {
        if (email.isNotEmpty() && password.isNotEmpty() &&
            retypedPassword.isNotEmpty()) {
            if (password == retypedPassword) {
                viewModelScope.launch(Dispatchers.Main) {
                    val result = async { attemptSignUp() }

                    _signUp.value = result.await() != null
                }
            } else {
                _passwordMismatch.value = true
            }
        } else {
            _emptyFields.value = true
        }
    }

    private suspend fun attemptSignUp(): AuthResult? {
        return try {
            val attempt = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).await()

            attempt
        } catch (e: Exception) {
            Log.e("NewsAPI", "Failed to fetch news: ${e.message}")   //Figure out logs
            null
        }
    }

    fun alreadyRegisteredTextClicked() {
        _alreadyRegistered.value = true
    }
}