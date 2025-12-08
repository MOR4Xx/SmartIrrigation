package com.ifmaker.smartirrigation.ui.Activity

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.databinding.FragmentIrrigacaoBinding
import com.ifmaker.smartirrigation.ui.ViewModel.EditarUsuarioViewModel


class EditarPerfil : AppCompatActivity() {

    private val viewModel: EditarUsuarioViewModel by viewModels()
    private lateinit var binding: EditarUsuarioViewModel
    private lateinit var btnConfirmar: Button
    private lateinit var btnCancelar: TextView
    private lateinit var edtNome: EditText
    private lateinit var edtSenha: EditText
    private lateinit var edtconfirmarSenha: EditText
    private lateinit var btnMostrarSenha: ImageView
    private lateinit var btnMostrarConfirmar: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)
        enableEdgeToEdge()

        carregarInterface()

        btnConfirmar.setOnClickListener {
            hideKeyboard()
            observarViewModel()

            val nome = edtNome.text.toString()
            val senha = edtSenha.text.toString()
            val confirmar = edtconfirmarSenha.text.toString()

            if (senha != confirmar) {
                mostrarSnackbar(it, "As senhas não coincidem")
            } else {
                viewModel.editarPerfil(nome, senha, confirmar)
            }
        }

        btnMostrarSenha.setOnClickListener { togglePassword(edtSenha, btnMostrarSenha) }
        btnMostrarConfirmar.setOnClickListener { togglePassword(edtNome, btnMostrarConfirmar) }
        btnCancelar.setOnClickListener {finish()}
    }

    private fun observarViewModel() {
        viewModel.resultado.observe(this) { (sucesso, mensagem) ->
            val view = findViewById<View>(R.id.edtPerfil)
            mostrarSnackbar(view, mensagem)

            if (sucesso) {
                btnConfirmar.postDelayed({ finish() }, 1000)
            }
        }
    }

    private fun carregarInterface() {
        btnConfirmar = findViewById(R.id.btnConfirmarEdt)
        btnCancelar = findViewById(R.id.btnCancelEdt)
        edtNome = findViewById(R.id.inputNomeEdt)
        edtSenha = findViewById(R.id.inputSenhaEdt)
        edtconfirmarSenha = findViewById(R.id.inputConfirmar)
        btnMostrarSenha = findViewById(R.id.btnMostrarSenhaEdt)
        btnMostrarConfirmar = findViewById(R.id.btnMostrarConfirmarEdt)

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