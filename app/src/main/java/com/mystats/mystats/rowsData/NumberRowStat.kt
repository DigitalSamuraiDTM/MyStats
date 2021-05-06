package com.mystats.mystats.rowsData

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mystats.mystats.MainApplication

class NumberRowStat : RowStat {

    private  var data : Long? = null

    constructor(name : String, data : String?){
        setNameRow(name)
        if (data!=null){
            this.data = data as Long
        }
    }

    constructor()
    override fun drawRowToViewData(): ViewGroup {
        //todo DRAW NUMBER
        val v = LinearLayout(MainApplication.getContext())
        return (v)
    }

    override fun setData(s: Any?) {
        data = (s as Long)
    }

    override fun getTypeRow(): Int {
        return TYPE_INT
    }

    override fun getNameType(): String {
        return "Number"
    }
}