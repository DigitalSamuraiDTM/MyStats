package com.mystats.mystats.rowsData

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.Timestamp
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R

class DateRowStat : RowStat {

    //TODO переделать в тип даты
    private  var data : String? = null
    private  var editData : EditText? = null


    constructor(name : String, data : String?){
        setNameRow(name)
        this.data = data
    }

    constructor()



    override fun drawRowToViewData(writable : Boolean): View {
        val v  = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.row_with_edit,null)
        val text : TextView = v.findViewById(R.id.rowEdit_view_nameRow)
        text.setText(getNameRow())
        editData  = v.findViewById(R.id.rowEdit_edit_data)
        editData?.inputType = InputType.TYPE_CLASS_DATETIME
        //todo можно добавить кнопочку с календарем
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
        this.data = s as String
    }

    override fun getData(): Any? {
        return data
    }

    override fun getTypeRow(): Int {
        return TYPE_DATE
    }

    override fun getNameType(): String {
        return "Date"
    }

    override fun clone(): Any {
        val r = DateRowStat(getNameRow(),data)
        return r
    }


}