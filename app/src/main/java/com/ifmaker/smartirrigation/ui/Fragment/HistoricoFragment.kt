package com.ifmaker.smartirrigation.ui.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.databinding.FragmentHistoricoBinding
import com.ifmaker.smartirrigation.ui.Adapter.HistoricoAdapter
import com.ifmaker.smartirrigation.ui.ViewModel.historicoViewModel

class HistoricoFragment : Fragment() {

    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: historicoViewModel
    private lateinit var adapter: HistoricoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Arrumando saporra", "criando fragmento ")
        // Configuração do RecyclerView
        val recyclerView: RecyclerView = binding.listaHistorico
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        Log.d("Arrumando saporra", "configurando adapter e definindo view model")
        // ViewModel
        viewModel = ViewModelProvider(this).get(historicoViewModel::class.java)
        // Adapter
        adapter = HistoricoAdapter(emptyList(), requireContext())
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

