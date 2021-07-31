package com.mystats.mystats.rowsData

import android.annotation.SuppressLint
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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
    private var editData : EditText? = null
    constructor(name: String, data: String?) {
        setNameRow(name)
        this.data = data
    }

    constructor()



    override fun drawRowToViewData(writable : Boolean): View {
        val v  = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.row_with_edit,null)
        val text : TextView = v.findViewById(R.id.rowEdit_view_nameRow)
        text.setText(getNameRow())
        editData  = v.findViewById(R.id.rowEdit_edit_data)
        editData?.maxLines = 50
        editData?.minLines = 1

        editData?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (editData?.text.toString() != ""){
                    data = editData?.text.toString()
                }
            }
        })

        if(data !=null){
            editData?.setText(data)
        }
        if (!writable){

            editData?.isEnabled = false
            editData?.isClickable = false
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