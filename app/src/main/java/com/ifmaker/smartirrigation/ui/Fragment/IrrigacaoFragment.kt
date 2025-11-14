package com.ifmaker.smartirrigation.ui.Fragment

import android.R.attr.visibility
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ifmaker.smartirrigation.R

class IrrigacaoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAutomatico = view.findViewById<Button>(R.id.btnautomatico)
        val btnManual = view.findViewById<Button>(R.id.btnmanual)
        val ultimaIrrig: LinearLayout = view.findViewById(R.id.ultima_irri)
        val ativarIrri: LinearLayout = view.findViewById(R.id.card_ativar_irrigacao)
        val parametrosIrrigacao: LinearLayout = view.findViewById(R.id.card_parametros_irrigacao)
        val irrigacaoManual: LinearLayout = view.findViewById(R.id.card_irrigacao_manual)

        irrigacaoManual.setVisibility(View.GONE)
        parametrosIrrigacao.setVisibility(View.VISIBLE)
        ativarIrri.setVisibility(View.VISIBLE)





        btnManual.setOnClickListener {
            btnAutomatico.background = ContextCompat.getDrawable(btnAutomatico.context, R.drawable.button_border)
            btnAutomatico.setTextColor(ContextCompat.getColor(btnAutomatico.context, R.color.blue))
            btnManual.background = ContextCompat.getDrawable(btnManual.context, R.drawable.button)
            btnManual.setTextColor(ContextCompat.getColor(btnManual.context, R.color.white))

            irrigacaoManual.setVisibility(View.VISIBLE)
            parametrosIrrigacao.setVisibility(View.GONE)
            ativarIrri.setVisibility(View.GONE)

        }

        btnAutomatico.setOnClickListener {
            btnAutomatico.background = ContextCompat.getDrawable(btnAutomatico.context, R.drawable.button)
            btnAutomatico.setTextColor(ContextCompat.getColor(btnAutomatico.context, R.color.white))
            btnManual.background = ContextCompat.getDrawable(btnManual.context, R.drawable.button_border)
            btnManual.setTextColor(ContextCompat.getColor(btnManual.context, R.color.blue))

            irrigacaoManual.setVisibility(View.GONE)
            parametrosIrrigacao.setVisibility(View.VISIBLE)
            ativarIrri.setVisibility(View.VISIBLE)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_irrigacao, container, false)
    }


}