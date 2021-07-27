package com.mystats.mystats.rowsData

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.mystats.mystats.MainApplication
import java.io.Serializable
import java.util.*

abstract class RowStat :  Serializable, Cloneable {

    companion object{
        val TYPE_STRING : Int  = 0
        val TYPE_INT = 1
        val TYPE_DATE = 2
        val TYPE_STATE = 3

    }

    private lateinit var NameRow : String


    public abstract fun drawRowToViewData(writable : Boolean) : View


    public abstract fun setData(s: Any?)

    public abstract fun getData() : Any?

    public fun setNameRow(name : String){
        NameRow = name
    }
    public fun getNameRow() : String{
        return NameRow
    }
    public abstract fun getTypeRow() : Int

    public abstract fun getNameType() : String

    public abstract override fun clone(): Any
}