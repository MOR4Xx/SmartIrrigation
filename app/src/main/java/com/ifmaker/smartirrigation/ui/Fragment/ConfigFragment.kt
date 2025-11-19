package com.ifmaker.smartirrigation.ui.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ifmaker.smartirrigation.data.Model.OptionMenuGet
import com.ifmaker.smartirrigation.ui.Adapter.ConfigAdapter
import com.ifmaker.smartirrigation.ui.Adapter.OptionViewHolder
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.data.Model.OptionMenu
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

        val nomeUsr = view.findViewById<TextView>(R.id.infoNome)
        val infoTextView = view.findViewById<TextView>(R.id.infoUser)

        val recyclerView: RecyclerView = view.findViewById(R.id.recicleOption)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)

        viewModel.getPermissao()
        viewModel.getNome()

        viewModel.nome.observe(viewLifecycleOwner) { nome ->
            val nome = viewModel.nome.value
            nomeUsr.text = "${nome}"
        }

        viewModel.permissao.observe(viewLifecycleOwner) { permissao ->
            val permissao = viewModel.permissao.value
            infoTextView.text = "${permissao}"

            val allOptions = OptionMenuGet.getMenuList()
            val filteredOptions: List<OptionMenu>

            if (permissao == "Administrador") {
                filteredOptions = allOptions
            } else {
                filteredOptions = allOptions.filter { option ->
                    option.title != "Latitude do Sistema" &&
                            option.title != "Adicionar Novo Usuario" &&
                            option.title != "Tipo de Plantio"
                }
            }

            recyclerView.adapter = ConfigAdapter(
                requireContext(),
                filteredOptions,
                onClickOption(filteredOptions)
            )
        }

    }

    override fun onResume() {
        super.onResume()

    }


    fun onClickOption(listOption: List<OptionMenu>): ConfigAdapter.OptionOnClickListener {

        return object : ConfigAdapter.OptionOnClickListener {
            override fun onClick(holder: OptionViewHolder, index: Int) {
                val selected = listOption[index].title

                when (selected) {
                    "Aparencia" -> Log.d("CLICK_TEST", "Nenhuma ação associada")
                    "Latitude do Sistema" -> alterarLatitude()
                    "Tipo de Plantio" -> alterarTipoPlantio()
                    "Adicionar Novo Usuario" -> abrirCadastroUsuario()
                    "Logout" -> logout()
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

    @SuppressLint("MissingInflatedId")
    fun alterarTipoPlantio() {
        val view = layoutInflater.inflate(R.layout.popup_tipo_plantio, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val txtCultura = view.findViewById<TextView>(R.id.txtCulturaAtual)
        val txtKc = view.findViewById<TextView>(R.id.txtKcAtual)

        viewModel.getDados()

        viewModel.cultura.observe(viewLifecycleOwner) { cultura ->
            val cultura = viewModel.cultura.value
            txtCultura.text = "Cultura Atual: ${cultura}"
        }

        viewModel.fase.observe(viewLifecycleOwner) { fase ->
            val fase = viewModel.fase.value
            txtKc.text = "Kc Atual: ${fase}"
        }

        val btnCancel = view.findViewById<Button>(R.id.btnCancelPlantio)
        val btnAlterar = view.findViewById<Button>(R.id.btnConfirmarPlantio)

        val spinnerTipoCultura = view.findViewById<Spinner>(R.id.inputCultura)
        viewModel.getCultura()

        viewModel.listCultura.observe(viewLifecycleOwner) { plantio ->
            spinnerTipoCultura.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                plantio
            )
        }

        val spinnerKc = view.findViewById<Spinner>(R.id.inputKc)

        val fases = listOf("Fase 1", "Fase 2", "Fase 3", "Fase 4")
        spinnerKc.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                fases
            )


        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnAlterar.setOnClickListener {
            val selectedTipoCultura = spinnerTipoCultura.selectedItem.toString()
            val selectedKc = spinnerKc.selectedItem.toString()
            viewModel.alterarTipoCultura(selectedTipoCultura, selectedKc)
            dialog.dismiss()
        }

        dialog.show()

    }


    private fun logout() {
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