package com.ifmaker.smartirrigation.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifmaker.smartirrigation.data.Model.Historico
import com.ifmaker.smartirrigation.R
import java.lang.Double.toString

class HistoricoAdapter(
    private var historicoList: List<Historico>? = null,
    private val context: Context?
) : RecyclerView.Adapter<HistoricoViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HistoricoViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.adapter_historico,
            viewGroup,
            false)
        return HistoricoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val card = historicoList!![position]
        holder.dataHistorico.text = "Data: ${card.data}"
        holder.horaHistorico.text = "Hora: ${card.hora}"
        holder.qtd.text = "Volume de água: ${ toString(card.quantidade) } L"
        holder.modo.text = "Modo: ${card.modo}"
        holder.usr.text = "Usuário: ${card.usuario}"
    }

    override fun getItemCount(): Int {
        return historicoList?.size ?: 0
    }

    fun updateList(newList: List<Historico>) {
        historicoList = newList
        notifyDataSetChanged()
    }

}

class HistoricoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var dataHistorico: TextView = view.findViewById(R.id.data)
    var horaHistorico: TextView = view.findViewById(R.id.hora)
    var qtd: TextView = view.findViewById(R.id.qtd)
    var modo: TextView = view.findViewById(R.id.modo)
    var usr: TextView = view.findViewById(R.id.usuario)
}