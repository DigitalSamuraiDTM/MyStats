package com.mystats.mystats.new_record

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.AdapterRecord
import com.mystats.mystats.R
import com.mystats.mystats.my_statistics.InterfaceWithNewRecord
import com.mystats.mystats.rowsData.NoteStats
import com.mystats.mystats.rowsData.RowStat


class FragmentNewRecord : Fragment() {
    private lateinit var recyclerRecord : RecyclerView
    private lateinit var dataList : ArrayList<NoteStats>
    private lateinit var buttonFinish : Button
    private lateinit var  presenter : PresenterNewRecord
    private lateinit var  adapterRecord : AdapterRecord
    private lateinit var layoutData : ConstraintLayout
    private lateinit var layoutLoading : ConstraintLayout
    override fun onAttach(context: Context) {
        presenter = PresenterNewRecord(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutData = view.findViewById(R.id.fr_newRecord_layout_allData);
        layoutLoading = view.findViewById(R.id.fr_newRecord_layout_loading);
        recyclerRecord = view.findViewById(R.id.fr_newRecord_recycler_record)
        buttonFinish = view.findViewById(R.id.fr_newRecord_button_finish)
        buttonFinish.setOnClickListener{
            //this.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            //todo будут проверки на нулевость?
            presenter.createNewRecord(dataList[0],
                arguments?.getString("NAMESTAT").toString(),
                arguments?.getSerializable("MS") as InterfaceWithNewRecord?
            )

        }

        dataList = ArrayList()
        adapterRecord = AdapterRecord(dataList,true)
        recyclerRecord.adapter = adapterRecord

        dataList.add(arguments?.getSerializable("COLUMNS")!! as NoteStats)
        adapterRecord.notifyDataSetChanged()
        super.onViewCreated(view, savedInstanceState)
    }

    public fun showLoading(){
        layoutData.visibility = View.GONE
        layoutLoading.visibility = View.VISIBLE
    }

    public fun showDataLayout(){
        layoutData.visibility = View.VISIBLE
        layoutLoading.visibility = View.GONE
    }

    public fun showError(){
        layoutData.visibility = View.VISIBLE
        layoutLoading.visibility = View.GONE
        Toast.makeText(requireActivity(),"Something block our magic\n Check your Internet connection and try again", Toast.LENGTH_SHORT).show();
    }
}