package com.mystats.mystats.new_record

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.AdapterRecord
import com.mystats.mystats.R
import com.mystats.mystats.my_statistics.InterfaceWithNewRecord
import com.mystats.mystats.rowsData.RowStat


class FragmentNewRecord : Fragment() {
    private lateinit var recyclerRecord : RecyclerView
    private lateinit var dataList : ArrayList<ArrayList<RowStat>>
    private lateinit var buttonFinish : Button
    private lateinit var  presenter : PresenterNewRecord
    private lateinit var  adapterRecord : AdapterRecord
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
        recyclerRecord = view.findViewById(R.id.fr_newRecord_recycler_record)
        buttonFinish = view.findViewById(R.id.fr_newRecord_button_finish)
        buttonFinish.setOnClickListener{
            //this.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            //todo будут проверки на нулевость?
            adapterRecord.confirmData()
            presenter.createNewRecord(dataList[0],
                arguments?.getString("NAMESTAT").toString(),
                arguments?.getInt("SIZESTAT")!!.toInt(),
                arguments?.getSerializable("MS") as InterfaceWithNewRecord?
            )

        }

        dataList = ArrayList()
        adapterRecord = AdapterRecord(dataList,true)
        recyclerRecord.adapter = adapterRecord

        dataList.add(arguments?.getSerializable("COLUMNS")!! as java.util.ArrayList<RowStat>)
        adapterRecord.notifyDataSetChanged()
        super.onViewCreated(view, savedInstanceState)
    }

    public fun showLoading(){
        //TODO доделать, плюс убрать лесенку (попытаться)
    }

    public fun showDataLayout(){

    }

    public fun showError(){

    }
}