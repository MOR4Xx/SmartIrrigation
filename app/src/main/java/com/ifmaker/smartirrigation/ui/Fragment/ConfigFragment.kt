package com.ifmaker.smartirrigation.ui.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ifmaker.smartirrigation.data.Model.OptionMenuAdm
import com.ifmaker.smartirrigation.ui.Adapter.ConfigAdapter
import com.ifmaker.smartirrigation.ui.Adapter.OptionViewHolder
import com.ifmaker.smartirrigation.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConfigFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recicleOption)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, RecyclerView.VERTICAL)
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfigFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun onClickOption(): ConfigAdapter.OptionOnClickListener{
        return object : ConfigAdapter.OptionOnClickListener{
            override fun onClick(holder: OptionViewHolder, index: Int) {

            }
        }
    }


}