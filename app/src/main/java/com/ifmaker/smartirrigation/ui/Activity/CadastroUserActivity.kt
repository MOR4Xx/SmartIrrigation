package com.ifmaker.smartirrigation.ui.Activity

import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.databinding.ActivityCadastroUserBinding
import com.ifmaker.smartirrigation.ui.ViewModel.CadastroViewModel

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

            viewModel.cadastrarUsuario(nome, email, senha, confirmar, permissao)
        }

        btnMostrarSenha.setOnClickListener { togglePassword(inputSenha, btnMostrarSenha) }
        btnMostrarConfirmar.setOnClickListener {
            togglePassword(
                inputConfirmar,
                btnMostrarConfirmar
            )
        }
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
}