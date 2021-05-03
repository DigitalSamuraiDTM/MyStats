package com.mystats.mystats.rowsData

import android.view.ViewGroup

class DateRowStat : RowStat {

    //TODO переделать в тип даты
    private  var data : String? = null

    constructor(name : String, data : String?){
        setNameRow(name)
        this.data = data
    }

    constructor()

    override fun drawRowToViewData(): ViewGroup {
        TODO("Not yet implemented")
    }

    override fun setData(s: Any?) {
        TODO("Not yet implemented")
    }

    override fun getTypeRow(): Int {
        return TYPE_DATE
    }

    override fun getNameType(): String {
        return "Date"
    }
}