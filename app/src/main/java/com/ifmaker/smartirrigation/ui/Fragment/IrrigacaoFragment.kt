package com.ifmaker.smartirrigation.ui.Fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.data.Model.ParametrosIrrigacao
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
    private lateinit var parametrosIrrigacaoLayout: LinearLayout
    private lateinit var irrigacaoManual: LinearLayout
    private lateinit var btnAlterarParametros: Button
    private lateinit var btnAumentar: ImageButton
    private lateinit var btnDiminuir: ImageButton
    private lateinit var txtQuantidade: TextView
    private lateinit var txtLitros: TextView
    private lateinit var slider: SeekBar
    private lateinit var btnAtivarManual: Button
    private lateinit var btnAutorizarIrriAutomatica: Button

    private lateinit var ultimaIrrigacao: UltimaIrrigacao
    private lateinit var parametrosIrrigacao: ParametrosIrrigacao
    private var volumeAguaSelecionado: Int = 30
    private val VOLUME_MAXIMO = 100
    private val VOLUME_MINIMO = 0
    private var isAutorizacaoAutomaticaAtiva: Boolean = false
    private var timerIrrigacao: CountDownTimer? = null
    private var mode: Boolean = false

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

        viewModel.getPermissao()
        viewModel.permissao.observe(viewLifecycleOwner) {
            desativarControles(it)
        }

        viewModel.getModoIrrigacao()
        viewModel.modoIrrigacao.observe(viewLifecycleOwner) { modoIrrigacao ->
            this.mode = modoIrrigacao
            atualizarInterface(mode)
            configurarControlesManuais()
        }

        viewModel.getUltimaIrrigacao()
        viewModel.ultimaIrrigacao.observe(viewLifecycleOwner) { ultimaIrrigacao ->
            val ultimaIrrigacao = viewModel.ultimaIrrigacao.value

            this.ultimaIrrigacao = ultimaIrrigacao!!

            exibirUltimaIrrigacao(ultimaIrrigacao)
        }

        viewModel.getParametrosIrrigacao()
        viewModel.parametrosIrrigacao.observe(viewLifecycleOwner) { parametrosIrrigacao ->
            val parametrosIrrigacao = viewModel.parametrosIrrigacao.value

            this.parametrosIrrigacao = parametrosIrrigacao!!

            exibirParametros()
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
            popupParametros()
        }

        btnAutorizarIrriAutomatica.setOnClickListener {
            isAutorizacaoAutomaticaAtiva = !isAutorizacaoAutomaticaAtiva

            atualizarEstiloBotaoAutorizar(isAutorizacaoAutomaticaAtiva)

            viewModel.setAutorizacaoIrrigacaoAutomatica(isAutorizacaoAutomaticaAtiva)

            with(state.edit()) {
                putBoolean("AUTORIZACAO_AUTO_ATIVA", isAutorizacaoAutomaticaAtiva)
                apply()
            }

            val msg = if (isAutorizacaoAutomaticaAtiva) "Irrigação Automática Ativada"
            else "Irrigação Automática Desativada"
            mostrarSnackbar(it, msg)
        }

//        btnAtivarManual.setOnClickListener {
//            if (volumeAguaSelecionado <= 0) return@setOnClickListener
//
//            viewModel.setQuantidadeAguaManual(volumeAguaSelecionado)
//            mostrarSnackbar(requireView(), "Irrigação Iniciada")
//
//            iniciarTimerIrrigacao(volumeAguaSelecionado)
//
//            btnAtivarManual.isEnabled = false
//            btnAtivarManual.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_border)
//            btnAtivarManual.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
//
//        }

    }
    private fun popupParametros() {
        val view = layoutInflater.inflate(R.layout.popup_parametros, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val inputEspacoPlanta = view.findViewById<EditText>(R.id.inputEspacoPlanta)
        val inputEspacoLinha = view.findViewById<EditText>(R.id.inputEspacoLinha)
        val inputVazaoGotejador = view.findViewById<EditText>(R.id.inputVazaoGotejador)
        val inputGotejadorsPlanta = view.findViewById<EditText>(R.id.inputGotejadoresPlanta)
        val inputQuantidadePlanta = view.findViewById<EditText>(R.id.inputQuantidadePlanta)
        val inputUmidadeMinima = view.findViewById<EditText>(R.id.inputUmidadeMinima)
        val btnCancel = view.findViewById<Button>(R.id.btnCancelParametro)
        val btnAlterar = view.findViewById<Button>(R.id.btnConfirmarParametro)

        viewModel.getParametrosIrrigacao()
        val dadosAtuais = viewModel.parametrosIrrigacao.value

        if (dadosAtuais != null) {
            inputEspacoPlanta.setText(dadosAtuais.espacamento_plantas_m)
            inputEspacoLinha.setText(dadosAtuais.espacamento_linhas_m)
            inputVazaoGotejador.setText(dadosAtuais.vazao_gotejador_lph)
            inputGotejadorsPlanta.setText(dadosAtuais.num_gotejadores_planta)
            inputQuantidadePlanta.setText(dadosAtuais.numero_total_plantas)
            inputUmidadeMinima.setText(dadosAtuais.limiar_umidade_minima)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnAlterar.setOnClickListener {
            val novoEspacoPlanta = inputEspacoPlanta.text.toString()
            val novoEspacoLinha = inputEspacoLinha.text.toString()
            val novaVazaoGotejador = inputVazaoGotejador.text.toString()
            val novoGotejadoresPlanta = inputGotejadorsPlanta.text.toString()
            val novaQuantidadePlanta = inputQuantidadePlanta.text.toString()
            val novaUmidadeMinima = inputUmidadeMinima.text.toString()

            viewModel.setParametrosIrrigacao(
                novoEspacoLinha,
                novoEspacoPlanta,
                novaUmidadeMinima,
                novoGotejadoresPlanta,
                novaQuantidadePlanta,
                novaVazaoGotejador
            )

            viewModel.getParametrosIrrigacao()
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun atualizarValorAgua(valor: Int) {
        slider.progress = valor
        atualizarTextosAgua()
    }

    private fun atualizarTextosAgua() {
        txtQuantidade.text = volumeAguaSelecionado.toString()
        txtLitros.text = "${volumeAguaSelecionado}L"
    }

    private fun exibirParametros() {
        viewModel.getParametrosIrrigacao()

        binding.parametrosIrrigacao.findViewById<TextView>(R.id.txtKc)
            .text = "Coeficiente de cultura (Kc): ${parametrosIrrigacao.kc}"
        binding.parametrosIrrigacao.findViewById<TextView>(R.id.txtEspacoPlanta)
            .text = "Espaçamento entre Plantas: ${parametrosIrrigacao.espacamento_plantas_m}m"
        binding.parametrosIrrigacao.findViewById<TextView>(R.id.txtEspacoLinha)
            .text = "Largura do canteiro: ${parametrosIrrigacao.espacamento_linhas_m}m"
        binding.parametrosIrrigacao.findViewById<TextView>(R.id.txtVazaoGotejador)
            .text = "Vazão do Gotejador: ${parametrosIrrigacao.vazao_gotejador_lph}L/h"
        binding.parametrosIrrigacao.findViewById<TextView>(R.id.txtGotejadores)
            .text = "Gotejadores por planta: ${parametrosIrrigacao.num_gotejadores_planta}"
        binding.parametrosIrrigacao.findViewById<TextView>(R.id.txtQtdPlanta)
            .text = "Quantidade de Plantas: ${parametrosIrrigacao.numero_total_plantas}"
        binding.parametrosIrrigacao.findViewById<TextView>(R.id.txtUmidade)
            .text = "Umidade mínima do Solo: ${parametrosIrrigacao.limiar_umidade_minima}%"
    }

    private fun exibirUltimaIrrigacao(ultimaIrrigacao: UltimaIrrigacao?) {
        val data = ultimaIrrigacao?.data ?: "--/--/--"
        val hora = ultimaIrrigacao?.hora ?: "--:--"
        val tempo = ultimaIrrigacao?.tempo ?: "-"
        val volume_por_planta = ultimaIrrigacao?.volume_por_planta ?: "-"
        val volume_total = ultimaIrrigacao?.volume_total ?: "-"
        val umidade_atual = ultimaIrrigacao?.umidade_atual ?: "-"

        binding.ultimaIrrigacao.findViewById<TextView>(R.id.data_ultima_irri).text = "Data: $data"
        binding.ultimaIrrigacao.findViewById<TextView>(R.id.hora_ultima_irri).text = "Hora: $hora"
        binding.ultimaIrrigacao.findViewById<TextView>(R.id.tempo_ultima_irri).text =
            "Tempo: $tempo"
        binding.ultimaIrrigacao.findViewById<TextView>(R.id.vol_ultima_irri).text =
            "Volume de água p/ Planta: $volume_por_planta"
        binding.ultimaIrrigacao.findViewById<TextView>(R.id.vol_total_ultima_irri).text =
            "Volume total de água: $volume_total"
        binding.ultimaIrrigacao.findViewById<TextView>(R.id.umid_ultima_irri).text =
            "Umidade antes da Irrigação: $umidade_atual%"
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

                iniciarTimerIrrigacao(volumeAguaSelecionado)

                btnAtivarManual.isEnabled = false
                btnAtivarManual.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_border)
                btnAtivarManual.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            }
        }
    }

    private fun desativarControles(isAdm: Boolean){
        if (!isAdm){
            btnManual.isEnabled = false
            btnAutomatico.isEnabled = false
            btnAtivarManual.isEnabled = false
            btnAlterarParametros.isEnabled = false
            btnAutorizarIrriAutomatica.isEnabled = false
            btnAumentar.isEnabled = false
            btnDiminuir.isEnabled = false
            slider.isEnabled = false
        }
    }
    private fun salvarPreferencia(isManual: Boolean) {
        val sharedPref = requireActivity().getSharedPreferences("AppIrrigacao", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            viewModel.setModoIrrigacao(if (isManual) "manual" else "automatico")
            apply()
        }
    }

    private fun atualizarInterface(isManual: Boolean) {
        val context = requireContext()

        if (isManual) {
            btnAutomatico.background = ContextCompat.getDrawable(context, R.drawable.button_border)
            btnAutomatico.setTextColor(
                ContextCompat.getColor(context,R.color.blue))
            btnManual.background =
                ContextCompat.getDrawable(context, R.drawable.button) // Assumi drawable "button"
            btnManual.setTextColor(ContextCompat.getColor(context, R.color.white))

            irrigacaoManual.visibility = View.VISIBLE
            parametrosIrrigacaoLayout.visibility = View.GONE
            ativarIrri.visibility = View.GONE
        } else {
            btnAutomatico.background = ContextCompat.getDrawable(context, R.drawable.button)
            btnAutomatico.setTextColor(ContextCompat.getColor(context, R.color.white))

            btnManual.background = ContextCompat.getDrawable(context, R.drawable.button_border)
            btnManual.setTextColor(ContextCompat.getColor(context, R.color.blue))

            irrigacaoManual.visibility = View.GONE
            parametrosIrrigacaoLayout.visibility = View.VISIBLE
            ativarIrri.visibility = View.VISIBLE
        }
    }

    private fun atualizarEstiloBotaoAutorizar(ativo: Boolean) {
        val context = requireContext()

        if (ativo) {
            btnAutorizarIrriAutomatica.text = "Desativar"
            btnAutorizarIrriAutomatica.background =
                ContextCompat.getDrawable(context, R.drawable.button_border)
            btnAutorizarIrriAutomatica.setTextColor(ContextCompat.getColor(context, R.color.blue))
        } else {
            btnAutorizarIrriAutomatica.text = "Ativar"
            btnAutorizarIrriAutomatica.background =
                ContextCompat.getDrawable(context, R.drawable.button)
            btnAutorizarIrriAutomatica.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    private fun definirInterface(view: View) {
        btnAutomatico = view.findViewById(R.id.btnautomatico)
        btnManual = view.findViewById(R.id.btnmanual)
        ultimaIrrig = view.findViewById(R.id.ultima_irrigacao)
        ativarIrri = view.findViewById(R.id.card_ativar_irrigacao)
        parametrosIrrigacaoLayout = view.findViewById(R.id.parametrosIrrigacao)
        irrigacaoManual = view.findViewById(R.id.card_irrigacao_manual)
        btnAumentar = view.findViewById(R.id.btnAumentar)
        btnDiminuir = view.findViewById(R.id.btnDiminuir)
        txtQuantidade = view.findViewById(R.id.txtQuantidade)
        txtLitros = view.findViewById(R.id.txtLitros)
        slider = view.findViewById(R.id.seekBarAgua)
        btnAtivarManual = view.findViewById(R.id.btnAtivarManual)
        btnAlterarParametros = view.findViewById(R.id.btn_alterar_parametros)
        btnAutorizarIrriAutomatica = view.findViewById(R.id.btn_ativar_irrigacao)
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

    private fun iniciarTimerIrrigacao(segundos: Int) {

        timerIrrigacao?.cancel()

        timerIrrigacao = object : CountDownTimer(segundos * 1000L, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val restante = (millisUntilFinished / 1000).toInt()
                btnAtivarManual.text = "${restante}s"
            }

            override fun onFinish() {
                btnAtivarManual.text = "Ativar"
                btnAtivarManual.background = ContextCompat.getDrawable(requireContext(), R.drawable.button)
                btnAtivarManual.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                btnAtivarManual.isEnabled = true

                mostrarSnackbar(requireView(), "Irrigação Finalizada!")
            }

        }.start()
    }
}