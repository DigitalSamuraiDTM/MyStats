package com.mystats.mystats

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.rowsData.RowStat

class AdapterRecord : RecyclerView.Adapter<AdapterRecord.ViewHolder> {

    private var data = ArrayList<ArrayList<RowStat>>()
    private var writable : Boolean
    private lateinit var root : ViewGroup

    constructor(data: ArrayList<ArrayList<RowStat>>, writable : Boolean){
        this.data = data;
        this.writable = writable
    }

    public fun setRoot(root : ViewGroup){
        this.root = root
    }
    constructor(writable: Boolean){
        this.writable = writable
    }

    public fun setArrayData(data: ArrayList<ArrayList<RowStat>>){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mainLay = LinearLayout(parent.context)

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.topMargin = 10
        params.bottomMargin = 5
        params.marginStart = 15
        params.marginEnd = 15
        mainLay.elevation = 10.0f
        mainLay.background = ContextCompat.getDrawable(MainApplication.getContext(),R.drawable.recycler_record_stat_drawable)
        mainLay.id = "item_recyclerRecord_mainLay".hashCode()
        mainLay.layoutParams = params
        mainLay.orientation = LinearLayout.VERTICAL
        return ViewHolder(mainLay)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lay : LinearLayout = holder.itemView.findViewById("item_recyclerRecord_mainLay".hashCode())
        for(i : Int in 0..data[position].size-1){
                //todo навести красоту
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