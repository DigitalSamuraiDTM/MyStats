package com.mystats.mystats.new_statistic

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.R
import com.mystats.mystats.rowsData.RowStat

class AdapterRowsStats : RecyclerView.Adapter<AdapterRowsStats.ViewHolder> {
    private  var data : ArrayList<RowStat>;

    constructor(data : ArrayList<RowStat>){
        this.data = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_column_stats,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.editTypeRow.setText(data.get(position).getNameType())
        holder.data = data.get(position)
        holder.viewNameRow.setText(data.get(position).getNameRow())
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
         var editTypeRow : EditText
         var viewNameRow : TextView
         var ButtonDelete : ImageButton
         lateinit var data : RowStat
        init {
            editTypeRow  = itemView.findViewById(R.id.item_rowStat_edit_rowType)
            viewNameRow  = itemView.findViewById(R.id.item_rowStat_view_nameRow)
            ButtonDelete = itemView.findViewById(R.id.item_rowStat_button_delete)
            ButtonDelete.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            when(p0?.id){
                R.id.item_rowStat_button_delete ->{
                    //todo delete row
                }
            }
        }

    }

}