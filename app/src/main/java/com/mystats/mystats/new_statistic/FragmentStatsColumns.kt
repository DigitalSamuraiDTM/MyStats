package com.mystats.mystats.new_statistic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.DialogNewRow
import com.mystats.mystats.InterfaceForDialogNewRow
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R
import com.mystats.mystats.rowsData.RowStat
import com.mystats.mystats.rowsData.StringRowStat


class FragmentStatsColumns : Fragment(), InterfaceForDialogNewRow{
    private lateinit var recyclerColumns : RecyclerView
    private lateinit var buttonNewRow : Button
    private lateinit var buttonReady : Button
    private lateinit var adapterRecycler : AdapterRowsStats
    private lateinit var data: ArrayList<RowStat>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats_columns, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerColumns = view.findViewById(R.id.fr_columnsStats_recycler_rows)
        data = ArrayList()
        adapterRecycler = AdapterRowsStats(data)
        recyclerColumns.adapter = adapterRecycler
        data.add(StringRowStat("Obama", "Pytin"))
        recyclerColumns.adapter?.notifyDataSetChanged()


        buttonNewRow  = view.findViewById(R.id.fr_columnStats_button_newRow)
        buttonNewRow.setOnClickListener {


            val dialog = DialogNewRow(this)
            dialog.show(requireActivity().supportFragmentManager,"NewRow")

        }
        buttonReady  = view.findViewById(R.id.fr_columnsStats_button_complete)
        buttonReady.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                //TODO запрос в базу с сохранением данных
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }
    fun showLoading(){
        //todo
    }
    fun hideLoading(){
        //todo
    }
    fun showError(){
        //todo
    }

    override fun getData(data: RowStat?) {
        this.data.add(data!!)
    }

}