package com.mystats.mystats.new_statistic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.R

class AdapterRowsStats() : RecyclerView.Adapter<AdapterRowsStats.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var
        init {
          //todo init object
        }

    }
    //todo типы данных. Сделать класс, который в себе будет хранить
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_column_stats,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 0
    }

}