package com.ifmaker.smartirrigation.ui.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.data.Model.UltimaIrrigacao
import com.ifmaker.smartirrigation.databinding.FragmentIrrigacaoBinding
import com.ifmaker.smartirrigation.ui.ViewModel.IrrigacaoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IrrigacaoFragment : Fragment() {

    private var _binding: FragmentIrrigacaoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IrrigacaoViewModel

    // Views Globais
    private lateinit var btnAutomatico: Button
    private lateinit var btnManual: Button
    private lateinit var ultimaIrrig: LinearLayout
    private lateinit var ativarIrri: LinearLayout
    private lateinit var parametrosIrrigacao: LinearLayout
    private lateinit var irrigacaoManual: LinearLayout
    private lateinit var btnAlterarParametros: Button
    private lateinit var btnAumentar: ImageButton
    private lateinit var btnDiminuir: ImageButton
    private lateinit var txtQuantidade: TextView
    private lateinit var txtLitros: TextView
    private lateinit var slider: SeekBar
    private lateinit var btnAtivarManual: Button
    private lateinit var btnAutorizarIrri: Button
    private lateinit var ultimaIrrigacao: UltimaIrrigacao
    private var volumeAguaSelecionado: Int = 30
    private val VOLUME_MAXIMO = 100
    private val VOLUME_MINIMO = 0


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

        definirInterface(_binding!!.root)

        val state = requireActivity().getSharedPreferences("AppIrrigacao", Context.MODE_PRIVATE)
        val isManualMode = state.getBoolean("MODO_MANUAL", false)

        atualizarInterface(isManualMode)
        configurarControlesManuais()

        viewModel.getUltimaIrrigacao()

        viewModel.ultimaIrrigacao.observe(viewLifecycleOwner) { ultimaIrrigacao ->
            val ultimaIrrigacao = viewModel.ultimaIrrigacao.value

            this.ultimaIrrigacao = ultimaIrrigacao!!

            exibirUltimaIrrigacao(ultimaIrrigacao)
        }


        btnManual.setOnClickListener {
            salvarPreferencia(true)
            atualizarInterface(true)
            viewModel.setModoIrrigacao("manual")
        }

        btnAutomatico.setOnClickListener {
            salvarPreferencia(false)
            atualizarInterface(false)
            viewModel.setModoIrrigacao("automatico")
        }

        btnAlterarParametros.setOnClickListener {

        }

    }

    private fun configurarControlesManuais() {

        atualizarValorAgua(volumeAguaSelecionado)

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                volumeAguaSelecionado = progress
                atualizarTextosAgua()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnAumentar.setOnClickListener {
            if (volumeAguaSelecionado < VOLUME_MAXIMO) {
                volumeAguaSelecionado++
                atualizarValorAgua(volumeAguaSelecionado)
            }
        }

        btnDiminuir.setOnClickListener {
            if (volumeAguaSelecionado > VOLUME_MINIMO) {
                volumeAguaSelecionado--
                atualizarValorAgua(volumeAguaSelecionado)
            }
        }

        btnAtivarManual.setOnClickListener {
            if (volumeAguaSelecionado > 0) {
                viewModel.setQuantidadeAguaManual(volumeAguaSelecionado)
                mostrarSnackbar(it, "Irrigação ativada com sucesso!")
            }
        }
    }

    private fun atualizarValorAgua(valor: Int) {
        slider.progress = valor
        atualizarTextosAgua()
    }

    private fun atualizarTextosAgua() {
        txtQuantidade.text = volumeAguaSelecionado.toString()
        txtLitros.text = "${volumeAguaSelecionado}L"
    }

    private fun salvarPreferencia(isManual: Boolean) {
        val sharedPref =
            requireActivity().getSharedPreferences("AppIrrigacao", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("MODO_MANUAL", isManual)
            apply()
        }
    }

    private fun exibirUltimaIrrigacao(ultimaIrrigacao: UltimaIrrigacao?) {
        val data = ultimaIrrigacao?.data ?: "--/--/--"
        val hora = ultimaIrrigacao?.hora ?: "--:--"
        val tempo = ultimaIrrigacao?.tempo ?: "-"
        val volume_por_planta = ultimaIrrigacao?.volume_por_planta ?: "-"
        val volume_total = ultimaIrrigacao?.volume_total ?: "-"
        val umidade_atual = ultimaIrrigacao?.umidade_atual ?: "-"

        binding.ultimaIrri.findViewById<TextView>(R.id.data_ultima_irri).text = "Data: $data"
        binding.ultimaIrri.findViewById<TextView>(R.id.hora_ultima_irri).text = "Hora: $hora"
        binding.ultimaIrri.findViewById<TextView>(R.id.tempo_ultima_irri).text = "Tempo: $tempo"
        binding.ultimaIrri.findViewById<TextView>(R.id.vol_ultima_irri).text = "Volume de água p/ Planta: $volume_por_planta"
        binding.ultimaIrri.findViewById<TextView>(R.id.vol_total_ultima_irri).text = "Volume total de água: $volume_total"
        binding.ultimaIrri.findViewById<TextView>(R.id.umid_ultima_irri).text = "Umidade antes da Irrigação: $umidade_atual%"
    }

    private fun atualizarInterface(isManual: Boolean) {
        val context = requireContext()

        if (isManual) {
            btnAutomatico.background = ContextCompat.getDrawable(context, R.drawable.button_border)
            btnAutomatico.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.blue
                )
            ) // Assumi que "blue" existe no seu colors

            btnManual.background =
                ContextCompat.getDrawable(context, R.drawable.button) // Assumi drawable "button"
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

    private fun definirInterface(view: View) {
        btnAutomatico = view.findViewById(R.id.btnautomatico)
        btnManual = view.findViewById(R.id.btnmanual)
        ultimaIrrig = view.findViewById(R.id.ultima_irri)
        ativarIrri = view.findViewById(R.id.card_ativar_irrigacao)
        parametrosIrrigacao = view.findViewById(R.id.card_parametros_irrigacao)
        irrigacaoManual = view.findViewById(R.id.card_irrigacao_manual)
        btnAumentar = view.findViewById(R.id.btnAumentar)
        btnDiminuir = view.findViewById(R.id.btnDiminuir)
        txtQuantidade = view.findViewById(R.id.txtQuantidade)
        txtLitros = view.findViewById(R.id.txtLitros)
        slider = view.findViewById(R.id.seekBarAgua)
        btnAtivarManual = view.findViewById(R.id.btnAtivarManual)
        btnAlterarParametros = view.findViewById(R.id.btn_alterar_parametros)
        btnAutorizarIrri = view.findViewById(R.id.btn_ativar_irrigacao)
    }

    private fun mostrarSnackbar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.blue))
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE

        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}