package com.ifmaker.smartirrigation.ui.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.data.Model.UltimaIrrigacao
import com.ifmaker.smartirrigation.databinding.FragmentIrrigacaoBinding
import com.ifmaker.smartirrigation.ui.ViewModel.IrrigacaoViewModel

class IrrigacaoFragment : Fragment() {

    private var _binding: FragmentIrrigacaoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IrrigacaoViewModel
    private lateinit var btnAutomatico: Button
    private lateinit var btnManual: Button
    private lateinit var ultimaIrrig: LinearLayout
    private lateinit var ativarIrri: LinearLayout
    private lateinit var parametrosIrrigacao: LinearLayout
    private lateinit var irrigacaoManual: LinearLayout
    private lateinit var ultimaIrrigacao: UltimaIrrigacao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIrrigacaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(IrrigacaoViewModel::class.java)

        btnAutomatico = view.findViewById(R.id.btnautomatico)
        btnManual = view.findViewById(R.id.btnmanual)
        ultimaIrrig = view.findViewById(R.id.ultima_irri)
        ativarIrri = view.findViewById(R.id.card_ativar_irrigacao)
        parametrosIrrigacao = view.findViewById(R.id.card_parametros_irrigacao)
        irrigacaoManual = view.findViewById(R.id.card_irrigacao_manual)

        val state = requireActivity().getSharedPreferences("AppIrrigacao", Context.MODE_PRIVATE)
        val isManualMode = state.getBoolean("MODO_MANUAL", false)

        atualizarInterface(isManualMode)

        viewModel.getUltimaIrrigacao()

        viewModel.ultimaIrrigacao.observe(viewLifecycleOwner) { ultimaIrrigacao ->
            val ultimaIrrigacao = viewModel.ultimaIrrigacao.value

            this.ultimaIrrigacao = ultimaIrrigacao!!

            exibirUltimaIrrigacao(ultimaIrrigacao)
        }

        btnManual.setOnClickListener {
            salvarPreferencia(true)
            atualizarInterface(true)
        }

        btnAutomatico.setOnClickListener {
            salvarPreferencia(false)
            atualizarInterface(false)
        }
    }

    private fun exibirUltimaIrrigacao(ultimaIrrigacao: UltimaIrrigacao?){
        val data = ultimaIrrigacao?.data
        val hora = ultimaIrrigacao?.hora
        val tempo = ultimaIrrigacao?.tempo
        val volume_por_planta = ultimaIrrigacao?.volume_por_planta
        val volume_total = ultimaIrrigacao?.volume_total
        val umidade_atual = ultimaIrrigacao?.umidade_atual

        val txtData = view?.findViewById<TextView>(R.id.data_ultima_irri)
        val txtHora = view?.findViewById<TextView>(R.id.hora_ultima_irri)
        val txtTempo = view?.findViewById<TextView>(R.id.tempo_ultima_irri)
        val txtVolPlanta = view?.findViewById<TextView>(R.id.vol_ultima_irri)
        val txtVolTotal = view?.findViewById<TextView>(R.id.vol_total_ultima_irri)
        val txtUmid = view?.findViewById<TextView>(R.id.umid_ultima_irri)

        txtData?.text = "Data: $data"
        txtHora?.text = "Hora: $hora"
        txtTempo?.text = "Tempo: $tempo"
        txtVolPlanta?.text = "Volume de água p/ Planta: $volume_por_planta"
        txtVolTotal?.text = "Volume total de água: $volume_total"
        txtUmid?.text = "Umidade antes da Irrigação: $umidade_atual%"
    }

    private fun salvarPreferencia(isManual: Boolean) {
        val sharedPref = requireActivity().getSharedPreferences("AppIrrigacao", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("MODO_MANUAL", isManual)
            apply()
        }
    }
    private fun atualizarInterface(isManual: Boolean) {
        val context = requireContext()

        if (isManual) {
            btnAutomatico.background = ContextCompat.getDrawable(context, R.drawable.button_border)
            btnAutomatico.setTextColor(ContextCompat.getColor(context, R.color.blue)) // Assumi que "blue" existe no seu colors

            btnManual.background = ContextCompat.getDrawable(context, R.drawable.button) // Assumi drawable "button"
            btnManual.setTextColor(ContextCompat.getColor(context, R.color.white))

            irrigacaoManual.visibility = View.VISIBLE
            parametrosIrrigacao.visibility = View.GONE
            ativarIrri.visibility = View.GONE
        } else {
            btnAutomatico.background = ContextCompat.getDrawable(context, R.drawable.button)
            btnAutomatico.setTextColor(ContextCompat.getColor(context, R.color.white))

            btnManual.background = ContextCompat.getDrawable(context, R.drawable.button_border)
            btnManual.setTextColor(ContextCompat.getColor(context, R.color.blue))

            irrigacaoManual.visibility = View.GONE
            parametrosIrrigacao.visibility = View.VISIBLE
            ativarIrri.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}