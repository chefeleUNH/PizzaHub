package edu.newhaven.pizzahub.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.newhaven.pizzahub.R

class MenuItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
    var tvMenuName: TextView = view.findViewById(R.id.tv_menu_name)
    var tvPrice: TextView = view.findViewById(R.id.tv_price)
}