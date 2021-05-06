package com.mystats.mystats.rowsData

import android.text.BoringLayout
import android.view.ViewGroup

class StateRowStat : RowStat {


    //todo переделать в boolean
    private var data : Boolean? = null

    constructor(name : String, data : String?){
        setNameRow(name)
        if (data!=null){
            this.data = data as Boolean

        }
    }

    constructor()

    override fun drawRowToViewData(): ViewGroup {
        TODO("Not yet implemented")
    }

    override fun setData(s: Any?) {
        this.data = s as Boolean
    }

    override fun getTypeRow(): Int {
        return TYPE_STATE
    }

    override fun getNameType(): String {
        return "State"
    }
}