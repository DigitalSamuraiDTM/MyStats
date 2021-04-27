package com.mystats.mystats.rowsData

import android.app.ActionBar
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mystats.mystats.MainApplication
class StringRowStat : RowStat {
    private lateinit var data : String
    constructor(name : String, data : String){
        setNameRow(name)
        this.data = data
    }
    constructor(){

    }

    override fun drawRowToViewData(): ViewGroup {
        //todo DRAW STRING
        val v = LinearLayout(MainApplication.getContext())
        return (v)
    }


    override fun setData(s: Any) {
        data = (s as String)
    }

    override fun getTypeRow(): Int {
        return 0
    }

    override fun getNameType(): String {
        return "String"
    }
}