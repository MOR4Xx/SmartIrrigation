package com.ifmaker.smartirrigation.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifmaker.smartirrigation.data.Model.OptionMenu
import com.ifmaker.smartirrigation.R

class ConfigAdapter(
    private val context: Context,
    private val listOptions: List<OptionMenu>? = null,
    private val onClickListener: OptionOnClickListener
) : RecyclerView.Adapter<OptionViewHolder>() {
    interface OptionOnClickListener {
        fun onClick(holder: OptionViewHolder, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.adapter_option,
            viewGroup,
            false
        )
        return OptionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOptions?.size ?: 0
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = listOptions!![position]
        holder.optionName.text = option.title

        holder.itemView.setOnClickListener { onClickListener.onClick(holder, position) }
    }

}

class OptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var optionName: TextView = view.findViewById(R.id.optionName)

}