package com.mystats.mystats.rowsData

import android.annotation.SuppressLint
import android.os.Build
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R

class StringRowStat : RowStat {
    private  var data : String? = null

    constructor(name: String, data: String?) {
        setNameRow(name)
        this.data = data
    }

    constructor()

    override fun drawRowToViewData(writable : Boolean): View {
        val v  = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.row_with_edit,null)
        val text : TextView = v.findViewById(R.id.rowEdit_view_nameRow)
        text.setText(getNameRow())
        val editData : EditText = v.findViewById(R.id.rowEdit_edit_data)
        //editData.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        editData.maxLines = 50
        editData.minLines = 1

        //editData.setBackground(ContextCompat.getDrawable(MainApplication.getContext()))
        editData.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, hasFocus: Boolean) {
                if (!hasFocus){
                    data = editData.text.toString()
                }
            }

        })
        if(data !=null){
            editData.setText(data)
        }
        if (!writable){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                editData.focusable = View.NOT_FOCUSABLE
//            } else{
                //todo попробовать оба способа и проверить за одно и focusable
                //editData.inputType = InputType.TYPE_NULL
                    editData.isClickable = false;
            editData.isFocusable = false
                editData.setTextIsSelectable(false)
//            }
        }
        return (v)
    }


    override fun setData(s: Any?) {
        data = (s as String)
    }

    override fun getData() : Any?{
        return data
    }

    override fun getTypeRow(): Int {
        return TYPE_STRING
    }

    override fun getNameType(): String {
        return "String"
    }

    override fun clone(): Any {
        val r = StringRowStat(getNameRow(),data)
        return r
    }
}