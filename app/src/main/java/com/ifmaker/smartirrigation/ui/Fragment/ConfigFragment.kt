package com.ifmaker.smartirrigation.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ifmaker.smartirrigation.data.Model.OptionMenuAdm
import com.ifmaker.smartirrigation.ui.Adapter.ConfigAdapter
import com.ifmaker.smartirrigation.ui.Adapter.OptionViewHolder
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.ui.Activity.CadastroUserActivity
import com.ifmaker.smartirrigation.ui.Activity.LoginActivity

class ConfigFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_config, container, false)

    }

    fun onClickOption(): ConfigAdapter.OptionOnClickListener {
        val listOption = OptionMenuAdm.getMenuList()

        return object : ConfigAdapter.OptionOnClickListener {
            override fun onClick(holder: OptionViewHolder, index: Int) {
                val selected = listOption[index].title

                when (selected) {
//                    "Latitude do Sistema" -> alterarLatitude()
//                    "Tipo de Plantio" -> alterarTipoPlantio()
                    "Adicionar Novo Usuario" -> abrirCadastroUsuario()
                    "Logout" -> showLogoutDialog()
                    else -> Log.d("CLICK_TEST", "Nenhuma ação associada")
                }
            }
        }
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