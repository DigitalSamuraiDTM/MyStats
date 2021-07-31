package com.mystats.mystats.rowsData

import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R

class NumberRowStat : RowStat {

    private  var data : Long? = null
    private  var editData : EditText? = null

    constructor(name : String, data : Any?){
        setNameRow(name)
        if (data!=null){
            this.data = data as Long
        }
    }

    constructor()



    override fun drawRowToViewData(writable : Boolean): View {
        val v  = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.row_with_edit,null)
        val text : TextView = v.findViewById(R.id.rowEdit_view_nameRow)
        text.setText(getNameRow())
        editData  = v.findViewById(R.id.rowEdit_edit_data)
        editData?.inputType = (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        editData?.maxLines = 50
        editData?.minLines = 1
        editData?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (editData?.text.toString() != ""){
                    data = editData?.text.toString().toLong()
                }
            }

        })
        if(data !=null){
            editData?.setText(data.toString())
        }
        if (!writable){
                editData?.isEnabled = false
                editData?.isClickable = false

        }
        return (v)
    }

    override fun setData(s: Any?) {
        data = (s as Long)
    }

    override fun getData(): Any? {
        return data
    }

    override fun getTypeRow(): Int {
        return TYPE_INT
    }

    override fun getNameType(): String {
        return "Number"
    }

    override fun clone(): Any {
        val r = NumberRowStat(getNameRow(),data)
        return r
    }
}