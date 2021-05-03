package com.mystats.mystats.rowsData

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.mystats.mystats.MainApplication
import java.util.*

abstract class RowStat {

    companion object{
        val TYPE_STRING : Int  = 0
        val TYPE_INT = 1
        val TYPE_DATE = 2
        val TYPE_STATE = 3

    }

    private lateinit var NameRow : String


    public abstract fun drawRowToViewData() : ViewGroup


    public abstract fun setData(s: Any?)

    public fun setNameRow(name : String){
        NameRow = name
    }
    public fun getNameRow() : String{
        return NameRow
    }
    public abstract fun getTypeRow() : Int

    public abstract fun getNameType() : String
}