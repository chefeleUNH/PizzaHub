package edu.newhaven.pizzahub

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PizzeriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var name: TextView = view.findViewById(R.id.tv_name)
}