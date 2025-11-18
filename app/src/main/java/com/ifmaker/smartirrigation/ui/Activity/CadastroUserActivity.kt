package com.ifmaker.smartirrigation.ui.Activity

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.databinding.ActivityCadastroUserBinding
import com.ifmaker.smartirrigation.ui.ViewModel.CadastroViewModel
import kotlinx.coroutines.flow.callbackFlow

class CadastroUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroUserBinding
    private lateinit var viewModel: CadastroViewModel

    private lateinit var inputNome: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputSenha: EditText
    private lateinit var inputConfirmar: EditText
    private lateinit var inputPermissao: Spinner
    private lateinit var btnCadastrar: Button
    private lateinit var btnMostrarSenha: ImageView
    private lateinit var btnMostrarConfirmar: ImageView
    private lateinit var btnCancel: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        initViews()

        binding.btnConfirmar.setOnClickListener {
            val nome = binding.inputNome.text.toString()
            val email = binding.inputEmail.text.toString()
            val senha = binding.inputSenha.text.toString()
            val confirmar = binding.inputConfirmar.text.toString()
            val permissao = binding.spinnerPermissao.selectedItem.toString()

            hideKeyboard()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
                mostrarSnackbar(binding.root, "Preencha todos os campos")
            } else if (senha != confirmar) {
                mostrarSnackbar(binding.root, "As senhas não coincidem")
            } else {
                viewModel.cadastrarUsuario(nome, email, senha, confirmar, permissao)
            }
        }

        viewModel.errorMessage.observe(this) {
            mostrarSnackbar(binding.root, it)
        }


        viewModel.success.observe(this) {
            if (it) {
                mostrarSnackbar(binding.root, "Usuário cadastrado com sucesso")
                finish()
            } else {
                mostrarSnackbar(binding.root, "Erro ao cadastrar usuário")
            }
        }

        btnMostrarSenha.setOnClickListener { togglePassword(inputSenha, btnMostrarSenha) }
        btnMostrarConfirmar.setOnClickListener { togglePassword(inputConfirmar, btnMostrarConfirmar) }
        btnCancel.setOnClickListener {finish()}

    }

    private fun initViews() {
        inputNome = findViewById(R.id.inputNome)
        inputEmail = findViewById(R.id.inputEmail)
        inputSenha = findViewById(R.id.inputSenha)
        inputConfirmar = findViewById(R.id.inputConfirmar)
        inputPermissao = findViewById(R.id.spinnerPermissao)
        btnMostrarSenha = findViewById(R.id.btnMostrarSenha)
        btnMostrarConfirmar = findViewById(R.id.btnMostrarConfirmar)
        btnCadastrar = findViewById(R.id.btnConfirmar)
        btnCancel = findViewById(R.id.btnCancel)

        val niveis = listOf("Administrador", "Visualizador")
        inputPermissao.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            niveis
        )
    }

    private fun togglePassword(edit: EditText, icon: ImageView) {
        val isVisible =
            edit.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)

        if (isVisible) {
            edit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            icon.setImageResource(R.drawable.eyees_on)
        } else {
            edit.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            icon.setImageResource(R.drawable.eyees_off)
        }

        edit.setSelection(edit.text.length)
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