package com.mystats.mystats.rowsData

import android.os.Build
import android.text.BoringLayout
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R

class StateRowStat : RowStat {

    private  var checkBoxData : CheckBox? = null
    private var data : Boolean? = false

    constructor(name : String, data : Any?){
        setNameRow(name)
        if (data!=null){
            this.data = data as Boolean
        }
    }

    constructor()



    override fun drawRowToViewData(writable : Boolean): View {
        val v  = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.row_with_checkbox,null)

        val text : TextView = v.findViewById(R.id.rowCheckBox_view_nameRow)
        text.setText(getNameRow())
        checkBoxData  = v.findViewById(R.id.rowCheck_checkbox_data)
        checkBoxData?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                data = p1
            }
        })
        if(data !=null){
            checkBoxData?.isChecked = data!!
        }
        if (!writable){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                checkBoxData.focusable = View.NOT_FOCUSABLE
//            } else{
            checkBoxData?.isClickable = false;
            checkBoxData?.isFocusable = false

            //checkBoxData.setTextIsSelectable(false)
//            }
        }
        return (v)
    }

    override fun setData(s: Any?) {
        this.data = s as Boolean
    }

    override fun getData(): Any? {
        return data
    }

    override fun getTypeRow(): Int {
        return TYPE_STATE
    }

    override fun getNameType(): String {
        return "State"
    }

    override fun clone(): Any {
        val r = StateRowStat(getNameRow(),data)
        return r
    }
}