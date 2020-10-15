package edu.newhaven.pizzahub

import android.view.View
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PizzeriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imgLogo: ImageView = view.findViewById(R.id.iv_logo)
    var txtName: TextView = view.findViewById(R.id.tv_name)
}