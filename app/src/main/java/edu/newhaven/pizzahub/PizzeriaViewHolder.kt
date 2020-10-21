package edu.newhaven.pizzahub

import android.view.View
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PizzeriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var ivLogo: ImageView = view.findViewById(R.id.iv_logo)
    var tvName: TextView = view.findViewById(R.id.tv_name)
    var tvDistance: TextView = view.findViewById(R.id.tv_distance)
    var tvReadyIn: TextView = view.findViewById(R.id.tv_ready_in)
}