package com.mystats.mystats.new_statistic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.dialogs_fragments.DialogNewRow
import com.mystats.mystats.dialogs_fragments.InterfaceForDialogNewRow
import com.mystats.mystats.R
import com.mystats.mystats.rowsData.RowStat


class FragmentStatsColumns : Fragment(), InterfaceForDialogNewRow {
    private lateinit var recyclerColumns : RecyclerView
    private lateinit var buttonNewRow : Button
    private lateinit var buttonReady : Button
    private lateinit var adapterRecycler : AdapterRowsStats
    private lateinit var data: ArrayList<RowStat>
    private lateinit var layoutLoading : ConstraintLayout
    private lateinit var layoutData : ConstraintLayout

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
        recyclerColumns.adapter?.notifyDataSetChanged()

        layoutLoading = view.findViewById(R.id.fr_columnStats_layoutLoading)
        layoutData  = view.findViewById(R.id.fr_columnStats_lay_data)

        buttonNewRow  = view.findViewById(R.id.fr_columnStats_button_newRow)
        buttonNewRow.setOnClickListener {


            val dialog = DialogNewRow(this as InterfaceForDialogNewRow)
            dialog.show(requireActivity().supportFragmentManager,"NewRow")

        }
        buttonReady  = view.findViewById(R.id.fr_columnsStats_button_complete)
        buttonReady.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
               PresenterNewStats.getInstance()?.createNewStats(data, this@FragmentStatsColumns)
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }

    fun showLoading(){
        layoutData.visibility = View.GONE
        buttonReady.visibility = View.GONE
        layoutLoading.visibility = View.VISIBLE
    }

    fun hideLoading(){
        layoutData.visibility = View.VISIBLE
        buttonReady.visibility = View.VISIBLE
        layoutLoading.visibility = View.GONE
    }

    fun showError(error : Int){
        Log.d("FIRESTORE", "ERROR")
        //0 - empty, 1 - create columns, 2 - create settings stats
        when(error){
            0->{
                Toast.makeText(requireActivity(),"Empty columns", Toast.LENGTH_SHORT).show()
            }
            1->{
                Toast.makeText(requireActivity(),"Request was failed", Toast.LENGTH_SHORT).show()
            }
            2->{
                Toast.makeText(requireActivity(),"Request was failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getData(data: RowStat?) {
        this.data.add(data!!)
    }
}