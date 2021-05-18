package com.mystats.mystats.rowsData

import android.os.Build
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R

class NumberRowStat : RowStat {

    private  var data : Long? = null

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
        val editData : EditText = v.findViewById(R.id.rowEdit_edit_data)
        //todo нужен или не нужен тип ввода?
        editData.inputType = (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        editData.maxLines = 50
        editData.minLines = 1
        editData.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, hasFocus: Boolean) {
                if (!hasFocus){
                    if (editData.text.toString() !=""){
                        data = editData.text.toString().toLong()
                    }
                }
            }

        })
        if(data !=null){
            editData.setText(data.toString())
        }
        if (!writable){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                editData.focusable = View.NOT_FOCUSABLE
            } else{
                //todo попробовать оба способа и проверить за одно и focusable
                //editData.inputType = InputType.TYPE_NULL
                editData.setTextIsSelectable(false)
            }
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