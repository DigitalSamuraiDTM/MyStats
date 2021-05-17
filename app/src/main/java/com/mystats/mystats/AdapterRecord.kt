package com.mystats.mystats

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.rowsData.RowStat

class AdapterRecord : RecyclerView.Adapter<AdapterRecord.ViewHolder> {

    private var data = ArrayList<ArrayList<RowStat>>()
    private var writable : Boolean

    constructor(data: ArrayList<ArrayList<RowStat>>, writable : Boolean){
        this.data = data;
        this.writable = writable
    }

    constructor(writable: Boolean){
        this.writable = writable
    }

    public fun setArrayData(data: ArrayList<ArrayList<RowStat>>){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mainLay = LinearLayout(parent.context)

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.topMargin = 5
        params.marginStart = 5
        params.marginEnd = 5

        mainLay.id = "item_recyclerRecord_mainLay".hashCode()
        mainLay.layoutParams = params
        mainLay.orientation = LinearLayout.VERTICAL
        return ViewHolder(mainLay)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lay : LinearLayout = holder.itemView.findViewById("item_recyclerRecord_mainLay".hashCode())
        for(i : Int in 0..data[position].size-1){
                //todo навести красоту
                    //todo сохранять новую запись в бд
            val v = data[position][i].drawRowToViewData(writable)
            lay.addView(v)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    public fun setNewData(data : ArrayList<ArrayList<RowStat>>){
        this.data.clear()
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {

        }
    }
}