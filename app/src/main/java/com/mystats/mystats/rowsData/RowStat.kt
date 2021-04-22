package com.mystats.mystats.rowsData

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.mystats.mystats.MainApplication
import java.util.*

abstract class RowStat {
    private lateinit var NameRow : String

    public abstract fun drawRowToViewData() : ViewGroup


    public abstract fun setData(s : Any)

    public fun setNameRow(name : String){
        NameRow = name
    }
    public fun getNameRow() : String{
        return NameRow
    }
    public abstract fun getTypeRow() : Int
}