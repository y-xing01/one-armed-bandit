package com.example.onearmedbandit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatisticsAdapter(private val dataSet: Array<StatisticItem?>) :
    RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val desc: TextView

        init {
            title = view.findViewById(R.id.title)
            desc = view.findViewById(R.id.desc)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (dataSet[position] != null) {
            viewHolder.title.text = dataSet[position]!!.title
            viewHolder.desc.text = dataSet[position]!!.desc
        }
    }

    override fun getItemCount() = dataSet.size

}
