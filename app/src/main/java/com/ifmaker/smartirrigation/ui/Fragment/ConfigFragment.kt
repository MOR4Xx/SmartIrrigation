package com.ifmaker.smartirrigation.ui.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ifmaker.smartirrigation.data.Model.OptionMenuAdm
import com.ifmaker.smartirrigation.ui.Adapter.ConfigAdapter
import com.ifmaker.smartirrigation.ui.Adapter.OptionViewHolder
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.databinding.FragmentConfigBinding
import com.ifmaker.smartirrigation.ui.Activity.CadastroUserActivity
import com.ifmaker.smartirrigation.ui.Activity.LoginActivity
import com.ifmaker.smartirrigation.ui.ViewModel.ConfigViewModel

class ConfigFragment : Fragment() {

    private var _binding: FragmentConfigBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ConfigViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfigBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ConfigViewModel::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.recicleOption)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)

        val listOption = OptionMenuAdm.getMenuList()

        recyclerView.adapter = ConfigAdapter(requireContext(), listOption, onClickOption())

    }

    override fun onResume() {
        super.onResume()

    }


    fun onClickOption(): ConfigAdapter.OptionOnClickListener {
        val listOption = OptionMenuAdm.getMenuList()

        return object : ConfigAdapter.OptionOnClickListener {
            override fun onClick(holder: OptionViewHolder, index: Int) {
                val selected = listOption[index].title

                when (selected) {
                    "Latitude do Sistema" -> alterarLatitude()
//                    "Tipo de Plantio" -> alterarTipoPlantio()
                    "Adicionar Novo Usuario" -> abrirCadastroUsuario()
                    "Logout" -> showLogoutDialog()
                    else -> Log.d("CLICK_TEST", "Nenhuma ação associada")
                }
            }
        }
    }

    private fun alterarLatitude() {
        val view = layoutInflater.inflate(R.layout.popup_latitude, null)

        viewModel.getLatitude()

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val textView = view.findViewById<TextView>(R.id.txtLatitudeAtual)
        val btnCancel = view.findViewById<Button>(R.id.btnCancelLatitude)
        val btnAlterar = view.findViewById<Button>(R.id.btnConfirmarLatitude)
        val edtLatitude = view.findViewById<EditText>(R.id.inputLatitude)

        viewModel.latitude.observe(viewLifecycleOwner) { latitude ->
            val latitude = viewModel.latitude.value
            textView.text = "Latitude Atual: ${latitude}"
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnAlterar.setOnClickListener {
            val latitude = edtLatitude.text.toString().toDouble()
            viewModel.alterarLatitude(latitude)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun abrirCadastroUsuario() {
        Log.d("teste", "entrou no metodo de chamar view")
        val intent = Intent(requireContext(), CadastroUserActivity::class.java)
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        val view = layoutInflater.inflate(R.layout.notificacao_logout, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            dialog.dismiss()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            requireActivity().finish()
        }

        dialog.show()
    }

}