package edu.newhaven.pizzahub.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.newhaven.pizzahub.R

class PizzeriaView(view: View) : RecyclerView.ViewHolder(view) {
    var ivLogo: ImageView = view.findViewById(R.id.iv_logo)
    var tvName: TextView = view.findViewById(R.id.tv_name)
    var tvDistance: TextView = view.findViewById(R.id.tv_distance)
    var tvReadyIn: TextView = view.findViewById(R.id.tv_ready_in)
}