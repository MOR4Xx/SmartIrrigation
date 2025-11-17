package com.ifmaker.smartirrigation.ui.Activity

import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.databinding.ActivityCadastroUserBinding
import com.ifmaker.smartirrigation.ui.ViewModel.CadastroViewModel

class CadastroUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroUserBinding
    private lateinit var viewModel: CadastroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        initViews()
        observerUi()

        binding.btnConfirmar.setOnClickListener {
            val nome = binding.inputNome.text.toString()
            val email = binding.inputEmail.text.toString()
            val senha = binding.inputSenha.text.toString()
            val confirmar = binding.inputConfirmar.text.toString()
            val permissao = binding.spinnerPermissao.selectedItem.toString()

            viewModel.cadastrarUsuario(nome, email, senha, confirmar, permissao)
        }

        binding.btnMostrarSenha.setOnClickListener {
            togglePassword(binding.inputSenha, binding.btnMostrarSenha)
        }

        binding.btnMostrarConfirmar.setOnClickListener {
            togglePassword(binding.inputConfirmar, binding.btnMostrarConfirmar)
        }
    }

    private fun observerUi() {
//        viewModel.loading.observe(this) { loading ->
//            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
//        }
//
//        viewModel.errorMessage.observe(this) { msg ->
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//        }
//
//        viewModel.success.observe(this) { ok ->
//            if (ok) {
//                Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }
    }

    private fun initViews() {
        val niveis = listOf("Administrador", "Visualizador")
        binding.spinnerPermissao.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            niveis
        )
    }

    private fun togglePassword(edit: EditText, icon: ImageView) {
        val visible = edit.inputType ==
                (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)

        if (visible) {
            edit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            icon.setImageResource(R.drawable.eyees_off)
        } else {
            edit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            icon.setImageResource(R.drawable.eyees_on)
        }

        edit.setSelection(edit.text.length)
    }
}
