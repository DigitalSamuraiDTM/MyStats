package com.mystats.mystats.rowsData

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.InputType
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

    override fun confirmDataInstallation() {
        if (editData !=null){
            data = editData?.text.toString()
        }
    }

    override fun drawRowToViewData(writable : Boolean): View {
        val v  = LayoutInflater.from(MainApplication.getContext()).inflate(R.layout.row_with_edit,null)
        val text : TextView = v.findViewById(R.id.rowEdit_view_nameRow)
        text.setText(getNameRow())
        editData  = v.findViewById(R.id.rowEdit_edit_data)
        editData?.inputType = InputType.TYPE_CLASS_DATETIME
        //todo можно добавить кнопочку с календарем

        if(data !=null){
            editData?.setText(data)
        }
        if (!writable){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                editData?.focusable = View.NOT_FOCUSABLE
            } else{
                //todo попробовать оба способа и проверить за одно и focusable
                //editData.inputType = InputType.TYPE_NULL
                editData?.setTextIsSelectable(false)
            }
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