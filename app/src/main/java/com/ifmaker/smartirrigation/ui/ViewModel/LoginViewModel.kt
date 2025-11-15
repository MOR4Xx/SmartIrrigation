package com.ifmaker.smartirrigation.ui.ViewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun login(login: String, password: String) {
        // login logic
        val username = login
    	val password = password

        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->(
            if (task.isSuccessful) {
                // login success
                Log.d("Login", "Login success")
                _loginResult.value = true
            } else {
                // login failed
                Log.d("Login", "Login failed")
                _loginResult.value = false
            }
        )}
    }
}