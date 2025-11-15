package com.ifmaker.smartirrigation.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment // Import de Fragment
import androidx.fragment.app.viewModels // <--- IMPORTANTE: Use este import para Fragments
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifmaker.smartirrigation.databinding.FragmentHistoricoBinding
import com.ifmaker.smartirrigation.ui.Adapter.HistoricoAdapter
import com.ifmaker.smartirrigation.ui.ViewModel.HistoricoViewModel

class HistoricoFragment : Fragment() {

    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!

    // Apenas declare a variavel. Não inicialize manualmente depois.
    private val viewModel: HistoricoViewModel by viewModels()

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

        // Configuração do Adapter
        adapter = HistoricoAdapter(emptyList(), requireContext())

        binding.listaHistorico.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoricoFragment.adapter
        }

        // --- LINHA REMOVIDA AQUI (O erro estava aqui) ---
        // viewModel = ViewModelProvider(this).get(HistoricoViewModel::class.java)

        // Observando os dados
        viewModel.listHistorico.observe(viewLifecycleOwner) { lista ->
            adapter.updateList(lista)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}