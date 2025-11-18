package com.ifmaker.smartirrigation.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.ui.ViewModel.LoginViewModel
import android.view.inputmethod.InputMethodManager

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var btnLogin: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponents()

        btnLogin.setOnClickListener {
            hideKeyboard()

            val loginText = login.text.toString()
            val passwordText = password.text.toString()

            if (loginText.isEmpty() || passwordText.isEmpty()){
                mostrarSnackbar(it, "Preencha todos os campos")
            } else {
                viewModel.login(loginText, passwordText)
            }
        }

        viewModel.loginResult.observe(this) { success ->
            if (success) {
                mostrarSnackbar(btnLogin, "Login realizado com sucesso!")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                mostrarSnackbar(btnLogin, "Erro ao fazer login. Verifique seus dados.")
            }
        }
    }

    private fun initComponents(){
        login = findViewById(R.id.login_edt_text)
        password = findViewById(R.id.senha_edt_text)
        btnLogin = findViewById(R.id.btn_login)
    }

    private fun mostrarSnackbar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.blue))
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE

        snackbar.show()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}