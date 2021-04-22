package com.mystats.mystats.rowsData

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mystats.mystats.MainApplication

class NumberRowStat : RowStat() {
    private  var data : Int = -1

    override fun drawRowToViewData(): ViewGroup {
        //todo DRAW NUMBER
        val v = LinearLayout(MainApplication.getContext())
        return (v)
    }

    override fun setData(s: Any) {
        data = (s as Int)
    }

    override fun getTypeRow(): Int {
        return 1
    }
}