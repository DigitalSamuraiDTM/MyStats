package com.mystats.mystats.rowsData

import android.view.ViewGroup
import android.widget.LinearLayout
import com.mystats.mystats.MainApplication
class StringRowStat : RowStat {
    private  var data : String? = null

    constructor(name: String, data: String?) {
        setNameRow(name)
        this.data = data
    }

    constructor()

    override fun drawRowToViewData(): ViewGroup {
        //todo DRAW STRING
        val v = LinearLayout(MainApplication.getContext())
        return (v)
    }


    override fun setData(s: Any?) {
        data = (s as String)
    }

    override fun getTypeRow(): Int {
        return TYPE_STRING
    }

    override fun getNameType(): String {
        return "String"
    }
}